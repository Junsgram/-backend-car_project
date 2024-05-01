package com.pratice.car.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    // 파일 업로드 하기 - 경로, 원본이름, 바이트타입의 배열
    public String uploadFile(String uploadpath, String originalFile, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        // 확장자
        String extension = originalFile.substring(originalFile.lastIndexOf("."));
        // uuid 파일명 + 확장자
        String saveFileName = uuid.toString() + extension;
        // 경로와 파일명 더해줌
        String fileUploadUrl = uploadpath+"/"+saveFileName;
        // 출력 스트림
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();
        return saveFileName;
    }

    // 삭제하기
    public void deleteFile(String filePath) {
        // 저장된 경로를 이용하여 파일 객체 생성
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            System.out.println("파일을 삭제했습니다.");
        }else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
