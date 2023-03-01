package com.lec.spring.controller;

import com.lec.spring.domain.LeisureFileDTO;
import com.lec.spring.service.LeisrefileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// 데이터를 response
@RestController
public class LeisureFileController {

    @Value("${app.leisure.upload.path}")  // org.springframework.beans.factory.annotation.Value
    private String uploadDir;

    private LeisrefileService LfileService;

    @Autowired
    public void setLfileService(LeisrefileService LfileService) {
        this.LfileService = LfileService;
    }

    public LeisureFileController() {
        System.out.println(getClass().getName() + "() 생성");
    }

    // 파일 다운로드
    // id : 첨부파일의 id
    @RequestMapping("/leisure/download")
    public ResponseEntity<Object> download(Long id){

        if(id == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        LeisureFileDTO file = LfileService.findById(id);
        if(file == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        String sourceName = file.getSource();    // 원본 이름
        String fileName = file.getFile();        // 저장된 파일명

        String path = new File(uploadDir + File.separator + fileName).getAbsolutePath();

        try{
            // 파일 유형 (Mimetype) 추출
            String mimeType = Files.probeContentType(Paths.get(path));

            // 유형이 지정되지 않은 경우
            if(mimeType == null){
                mimeType = "application/octet-stream";
            }

            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath));

            // resposne header 세팅
            HttpHeaders headers = new HttpHeaders();
            // ↓ 원본 파일 이름(sourceName) 으로 다운로드 하게 하기위한 세팅
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment").filename(URLEncoder.encode(sourceName, "utf-8")).build());
            headers.setCacheControl("no-cache");
            headers.setContentType(MediaType.parseMediaType(mimeType));

            // ResponseEntity 리턴 (body, header, status)
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);  // 200
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);  // 409
        }
    }

}
