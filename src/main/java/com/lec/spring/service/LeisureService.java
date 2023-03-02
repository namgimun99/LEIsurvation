package com.lec.spring.service;


import com.lec.spring.domain.*;
import com.lec.spring.repository.LeisureFileRepository;
import com.lec.spring.repository.LeisureWriteRepository;
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
public class LeisureService {

    @Value("${app.leisure.upload.path}")
    private String uploadDir;
    private LeisureWriteRepository leisureWriteRepository;

    private UserRepository userRepository;
    private LeisureFileRepository leisureFileRepository;

    @Autowired
    public LeisureService(SqlSession sqlSession) {
        leisureWriteRepository = sqlSession.getMapper(LeisureWriteRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        leisureFileRepository = sqlSession.getMapper(LeisureFileRepository.class);
    }

    // db save
    public int leisureWrite(LeisureWrite leisureWrite,
                            Map<String, MultipartFile> files) {

        User user_id = U.getLoggedUser();
        user_id = userRepository.findById(user_id.getId());
        leisureWrite.setUser_id(user_id);

        int cnt = leisureWriteRepository.save(leisureWrite);

        addFiles(files, leisureWrite.getId());

        return cnt;
    }
//
//    public List<LeisureWrite> selectById(long id) {
//        List<LeisureWrite> list = new ArrayList<>();
//
//        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);
//
//        if (leisureWrite != null) {
//            // 첨부파일 정보 가져오기
//            List<LeisureFileDTO> fileList = leisureFileRepository.findByLeisure(leisureWrite.getId());
//            setImage(fileList);   // 이미지 파일 여부 세팅
//            leisureWrite.setFiles(fileList);
//
//            list.add(leisureWrite);
//        }
//
//        return list;
//    }

    public LeisureWrite selectById2(long id) {
        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);

        if (leisureWrite != null) {
            // 첨부파일 정보 가져오기
            List<LeisureFileDTO> fileList = leisureFileRepository.findByLeisure(leisureWrite.getId());
            setImage(fileList);   // 이미지 파일 여부 세팅
            leisureWrite.setFiles(fileList);

        }
        return leisureWrite;
    }


    // id: leisure_id
    private void addFiles(Map<String, MultipartFile> files, Long id) {
        if (files != null) {
            for (Map.Entry<String, MultipartFile> e : files.entrySet()) {
                System.out.println("\n첨부파일 정보: " + e.getKey());
                // U.printFileInfo(e.getValue());

                // upload file
                LeisureFileDTO file = upload2((MultipartFile) e.getValue());

//                LeisureFileDTO file = upload(e.getValue());

                if (file != null) {
                    file.setLeisure_id(id);
                    leisureFileRepository.save(file);
                }

            }
        }
    }

    private LeisureFileDTO upload2(MultipartFile mfile) {
//        MultipartFile mfile = (MultipartFile)files.get("upfile0");
        if(mfile != null) {
//        System.out.println("-----------------------------------------");
//        System.out.println(multipartFile.getOriginalFilename());
//        System.out.println("-----------------------------------------");
            // 'file save' is needed.

            File folder = new File("C:\\DevRoot\\Dropbox\\Web\\Leisurvation\\upload\\leisure");
            if (!folder.exists())
                folder.mkdirs(); // 폴더생성
            String originalFileName = mfile.getOriginalFilename();
            String saveFileName = originalFileName;
            try {
                mfile.transferTo(new File(folder, saveFileName));  // folder라는 폴더의 saveFileName이름으로 upload된 파일 실제 저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // System.out.println(saveFileName); // 테스트
            LeisureFileDTO leisureFileDTO = new LeisureFileDTO();
            leisureFileDTO.setSource(originalFileName);
            leisureFileDTO.setFile(saveFileName);
            return leisureFileDTO;
        }
        return null;
    }

    private LeisureFileDTO upload(MultipartFile multipartFile) {
        LeisureFileDTO attachment = null;

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null || originalFilename.length() == 0) return null;

        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String fileName = sourceName;

        File file = new File(uploadDir + File.separator + sourceName);
        if (file.exists()) {
            int pos = fileName.lastIndexOf(".");
            if (pos > -1) {
                String name = fileName.substring(0, pos);
                String ext = fileName.substring(pos + 1);
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        attachment = LeisureFileDTO.builder()
                .file(fileName)
                .source(sourceName)
                .build();

        return attachment;
    } // end upload

    //별점
    public Double getAvgstar(Long id){

        return leisureWriteRepository.selectAvgStar(id);
    }

    @Transactional
    public List<LeisureWrite> leisureDetail(long id) {
        List<LeisureWrite> list = new ArrayList<>();

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);



        if (leisureWrite != null) {
            List<LeisureFileDTO> files = leisureFileRepository.findByLeisure(leisureWrite.getId());
            setImage(files);
            leisureWrite.setFiles(files);
            list.add(leisureWrite);
        }

        return list;
    }


    private void setImage(List<LeisureFileDTO> fileList) {
        // upload 실제 물리적인 경로
        String realPath = new File(uploadDir).getAbsolutePath();

        for (LeisureFileDTO fileDto : fileList) {
            BufferedImage imgData = null;
            File f = new File(realPath, fileDto.getFile());  // 첨부파일에 대한 File 객체
            try {
                imgData = ImageIO.read(f);
                // ※ ↑ 파일이 존재 하지 않으면 IOExcepion 발생한다
                //   ↑ 이미지가 아닌 경우는 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }

            if (imgData != null) fileDto.setImage(true); // 이미지 여부 체크
        } // end for
    }

    public List<LeisureWrite> listprice() {
        return leisureWriteRepository.findprice();
    }

    public List<LeisureWrite> liststar() {
        return leisureWriteRepository.findstar();
    }

    public int deleteById(long id) {
        int result = 0;

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);
        if (leisureWrite != null) {
            result = leisureWriteRepository.delete(leisureWrite);
        }
        return result;
    }

    public int update(LeisureWrite leisurewrite
            , Map<String, MultipartFile> files // 새로 추가될 첨부파일들
            , Long[] delfile) {
        int result = 0;

        result = leisureWriteRepository.update(leisurewrite);

        // 첨부파일 추가
        addFiles(files, leisurewrite.getId());

        // 삭제할 첨부파일들은 삭제하기
        if(delfile != null){
            for(Long fileId : delfile){
                LeisureFileDTO file = leisureFileRepository.findById(fileId);
                if(file != null){
                    delFile(file); // 물리적으로 삭제
                   leisureFileRepository.delete(file); // db에서 삭제
                }
            }
        }

        return result;
    }// end update



    private void delFile(LeisureFileDTO file) {
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

    //     특정 글 삭제
    public int deleteById(Long id) {
        int result = 0;

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);
        if (leisureWrite != null) {

            // 물리적으로 저장된 첨부파일(들) 삭제
            List<LeisureFileDTO> fileList = leisureFileRepository.findByLeisure(id);
            if (fileList != null && fileList.size() > 0) {
                for (LeisureFileDTO file : fileList) {
                    delFile(file);
                }
            }

            result = leisureWriteRepository.delete(leisureWrite);
        }

        return result;
    }
}