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

    @Value("app.upload.path")
    private String uploadDir;
    private LeisureWriteRepository leisureWriteRepository;

    private UserRepository userRepository;
    private LeisureFileRepository leisureFileRepository;
    @Autowired
    public LeisureService(SqlSession sqlSession){
        leisureWriteRepository = sqlSession.getMapper(LeisureWriteRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        leisureFileRepository = sqlSession.getMapper(LeisureFileRepository.class);
    }



    public int leisureWrite(LeisureWrite leisureWrite,
                            Map<String, MultipartFile> files){

        User user_id = U.getLoggedUser();
        user_id = userRepository.findById(user_id.getId());
        leisureWrite.setUser_id(user_id);

        int cnt = leisureWriteRepository.save(leisureWrite);

        addFiles(files, leisureWrite.getId());

            return cnt;
    }
    public List<LeisureWrite> selectById(long id) {
        List<LeisureWrite> list = new ArrayList<>();

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);

        if(leisureWrite != null){
            // 첨부파일 정보 가져오기
            List<LeisureFileDTO> fileList = leisureFileRepository.findByLeisure(leisureWrite.getId());
            setImage(fileList);   // 이미지 파일 여부 세팅
            leisureWrite.setFiles(fileList);

            list.add(leisureWrite);
        }

        return list;
    }


    private void addFiles(Map<String, MultipartFile> files, Long id){
        if(files != null){
            for(Map.Entry<String, MultipartFile> e :files.entrySet()){
                System.out.println("\n첨부파일 정보: " + e.getKey());
                U.printFileInfo(e.getValue());

                LeisureFileDTO file = upload(e.getValue());

                if(file != null){
                    file.setLeisure_id(id);
                    leisureFileRepository.save(file);
                }

            }
        }
    }

    private LeisureFileDTO upload(MultipartFile multipartFile){
        LeisureFileDTO attachment = null;

        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String fileName = sourceName;

        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){
            int pos = fileName.lastIndexOf(".");
            if(pos > -1) {
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
    @Transactional
    public List<LeisureWrite> leisureDetail(long id) {
        List<LeisureWrite> list = new ArrayList<>();

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);

        if(leisureWrite != null){
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

        for(LeisureFileDTO fileDto : fileList) {
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
    public List<LeisureWrite> listprice(){
        return leisureWriteRepository.findprice();
    }

    public List<LeisureWrite> liststar(){
        return leisureWriteRepository.findstar();
    }

    public int deleteById(long id){
        int result = 0;

        LeisureWrite leisureWrite = leisureWriteRepository.findById(id);
        if(leisureWrite != null){
            result = leisureWriteRepository.delete(leisureWrite);
        }
        return result;
    }

    public int update(LeisureWrite leisurewrite) {
        return leisureWriteRepository.update(leisurewrite);
    }

}