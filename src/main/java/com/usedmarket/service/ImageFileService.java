package com.usedmarket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class ImageFileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{

        UUID uuid = UUID.randomUUID();

        //확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String imageName = uuid.toString() + extension;
        String imageUploadUrl = uploadPath + "/" + imageName;

        //생성자로 경로+파일명을넘겨 스트림생성
        FileOutputStream outputStream = new FileOutputStream(imageUploadUrl);
        //데이터를 스트림에 입력
        outputStream.write(fileData);
        //스트림을 닫음
        outputStream.close();
        return imageName;
    }

    public void deleteFile(String filePath) throws Exception{

        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일삭제완료");
        } else {
            log.info("파일을 찾을수없음");
        }
    }
}
