package com.lec.spring.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class U {

    public static void printFileInfo(MultipartFile file){
        String originalFileName = file.getOriginalFilename();

        if(originalFileName == null || originalFileName.length() == 0){
            System.out.println("\t파일이 없습니다");
            return;
        }

        BufferedImage bufferedImage = null;
        try{
            bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage != null){
                System.out.println("\t이미지 파일입니다");
            } else {
                System.out.println("\t이미지 파일이 아닙니다");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
