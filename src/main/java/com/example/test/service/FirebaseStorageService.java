package com.example.test.service;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.google.cloud.storage.Blob;
import javax.imageio.ImageIO;

@Service
public class FirebaseStorageService {
    //규칙 if request.auth != null;

    // [Profile] 프로필 업로드
    public String uploadFile(MultipartFile file, int userIdx) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        // 파일명에서 확장자 추출
        String originalFileName = file.getOriginalFilename();
    
        byte[] imageBytes;
        if (originalFileName != null && originalFileName.toLowerCase().endsWith(".png")) {
            imageBytes = file.getBytes();
        } else {
            imageBytes = convertImageToPNG(file);
        }

        String fileName = userIdx + "/" + userIdx + "profile.png";

        try {
            bucket.create(fileName, imageBytes, "image/png");
        } catch (Exception e) {
            e.printStackTrace();  
            throw new IOException("파일 업로드에 실패했습니다.", e);  
        }

        return String.format("https://storage.googleapis.com/%s", fileName);
    }

    private byte[] convertImageToPNG(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        
        // 이미지 파일을 BufferedImage로 변환
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        
        // 이미지가 null이면 예외를 던짐
        if (bufferedImage == null) {
            throw new IOException("이미지를 읽을 수 없습니다.");
        }

        // PNG로 변환하여 ByteArrayOutputStream에 저장
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);

        // 변환된 PNG 데이터를 byte 배열로 반환
        return byteArrayOutputStream.toByteArray();
    }
    

    // [Profile] 프로필 다운로드
    public InputStream loadFile(int userIdx) throws IOException {
        
        String fileName = userIdx + "/" + userIdx + "profile.png";

        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(fileName);

        if (blob == null) {
            throw new IOException("해당 프로필 이미지 파일을 찾을 수 없습니다.");
        }

        byte[] fileContent = blob.getContent();

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContent)) {
            return byteArrayInputStream; 
        }
    }

}