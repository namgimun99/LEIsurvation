package com.lec.spring.service;

import com.lec.spring.domain.Review;
import com.lec.spring.domain.ReviewFileDTO;
import com.lec.spring.domain.User;
import com.lec.spring.repository.ReviewFileRepository;
import com.lec.spring.repository.ReviewRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Value("${app.review.upload.path}")
    private String uploadDir;

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private ReviewFileRepository fileRepository;

    @Autowired
    public ReviewService(SqlSession sqlSession){
        reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        fileRepository = sqlSession.getMapper(ReviewFileRepository.class);
        System.out.println("ReviewService() 생성");
    }

    // 새글 작성
    public int review(Review review
            , Map<String, MultipartFile> files
    )
    {
        // 현재 로그인한 사용자
        User user_id = U.getLoggedUser();
        // session에 있는 정보, 일단 DB에서 다시 읽어온다.
        user_id = userRepository.findById(user_id.getId());
        review.setUser_id(user_id); // 글 작성자 세팅

        int cnt = reviewRepository.save(review);

        // 첨부파일 추가
        addFiles(files, review.getId());

        return cnt;
    }

    // 특정 글(id) 조회
    public List<Review> selectById(long id) {
        List<Review> list = new ArrayList<>();

        Review review = reviewRepository.findById(id);

        if(review != null){
            // 첨부파일 정보 가져오기
            List<ReviewFileDTO> fileList = fileRepository.findByReview(review.getId());
            setImage(fileList);   // 이미지 파일 여부 세팅
            review.setFiles(fileList);

            list.add(review);
        }

        return list;
    }

    // 특정 글(id) 의 첨부파일(들) 추가
    private void addFiles(Map<String, MultipartFile> files, Long id){
        if(files != null){
            for(Map.Entry<String, MultipartFile> e : files.entrySet()){
                // 첨부파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey()); //name 값
                U.printFileInfo(e.getValue());
                System.out.println();

                // 물리적인 파일 저장
                ReviewFileDTO file = upload(e.getValue());

                // 성공하면 DB에도 저장
                if(file != null){
                    file.setReview_id(id); // FK 설정
                    fileRepository.save(file); // INSERT
                }
            }
        }
    } // end addFiles()

    // 물리적으로 파일 저장, 중복괸 이름 rename 처리
    private ReviewFileDTO upload(MultipartFile multipartFile){
        ReviewFileDTO attachment = null;

        // 담긴 파일이 없으면 pass~
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        // 원본 파일 명
        //                  org.springframework.util.StringUtils
        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // 저장될 파일 명
        String fileName = sourceName;

        // 파일이 중복되는지 확인
        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){ //  이미 존재하는 파일명이라면 수정한다.
            int pos = fileName.lastIndexOf(".");
            if(pos > -1) { // 확장자가 있는 경우
                String name = fileName.substring(0, pos);  // 파일'이름'
                String ext = fileName.substring(pos + 1);  // 파일'확장명'

                // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

        // 저장할 피일명
        System.out.println("fileName: " + fileName);

        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다

            // java.nio.file.Files
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING   // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            e.printStackTrace();
            // 예외처리는 여기서.
            //throw new FileStorageException("Could not store file : " + multipartFile.getOriginalFilename());
        }

        attachment = ReviewFileDTO.builder()
                .file(fileName)   // 저장된 이름
                .source(sourceName)  // 원본이름
                .build();

        return attachment;
    }// end upload

    // 특정 id의 글 조회
    @Transactional
    public List<Review> detail(Long id) {
        List<Review> reviewList = new ArrayList<>();
        Review r = reviewRepository.findById(id);
        if(r != null){
            // 첨부파일 정보 가져오기
            List<ReviewFileDTO> files = fileRepository.findByReview(r.getId());
            setImage(files);// 이미지 파일 여부 세팅
            r.setFiles(files);

            reviewList.add(r);
        }
        return reviewList;
    }

    // [이미지 파일 여부 세팅]
    private void setImage(List<ReviewFileDTO> fileList) {
        // upload 실제 물리적인 경로
        String realPath = new File(uploadDir).getAbsolutePath();

        for(ReviewFileDTO fileDto : fileList) {
            BufferedImage imgData = null;
            File f = new File(realPath, fileDto.getFile());  // 첨부파일에 대한 File 객체
            try {
                imgData = ImageIO.read(f);
                // ※ ↑ 파일이 존재 하지 않으면 IOExcepion 발생한다
                //   ↑ 이미지가 아닌 경우는 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }

            if(imgData != null) fileDto.setImage(true); // 이미지 여부 체크
        } // end for
    }

    // 모든 글 조회
    public List<Review> list(){
        return reviewRepository.findAll();
    }

    // 특정 글 수정
    public int update(Review review
            , Map<String, MultipartFile> files // 새로 추가될 첨부파일들
            , Long[] delfile){  // 삭제될 첨부파일들

        int result = 0;

        result = reviewRepository.update(review);

        // 첨부파일 추가
        addFiles(files, review.getId());

        // 삭제할 첨부파일들은 삭제하기
        if(delfile != null){
            for(Long fileId : delfile){
                ReviewFileDTO file = fileRepository.findById(fileId);
                if(file != null){
                    delFile(file); // 물리적으로 삭제
                    fileRepository.delete(file); // db에서 삭제
                }
            }
        }

        return result;
    }// end update

    // 특정 첨부파일(id)를 물리적으로 삭제
    private void delFile(ReviewFileDTO file) {
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, file.getFile()); // 물리적으로 저장된 파일들이 삭제 대상
        System.out.println("삭제시도--> " + f.getAbsolutePath());

        if (f.exists()) {
            if (f.delete()) { // 삭제!
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        } // end if
    }// end delFile

    // 특정 글 삭제
    public int deleteById(Long id){
        int result = 0;

        Review review = reviewRepository.findById(id);
        if(review != null){

            // 물리적으로 저장된 첨부파일(들) 삭제
            List<ReviewFileDTO> fileList = fileRepository.findByReview(id);
            if(fileList != null && fileList.size() > 0) {
                for(ReviewFileDTO file : fileList) {
                    delFile(file);
                }
            }

            result = reviewRepository.delete(review);
        }

        return result;
    }

    // leisure, reserve 정보 조회
    public List<Review> RLlist(Long reservation_id) {
        return reviewRepository.findByRSId(reservation_id);
    }


}
