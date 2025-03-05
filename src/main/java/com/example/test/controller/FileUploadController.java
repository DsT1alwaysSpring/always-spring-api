package com.example.test.controller;

import com.example.test.service.FirebaseStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;  


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FirebaseStorageService firebaseStorageService;

    public FileUploadController(FirebaseStorageService firebaseStorageService) {
        this.firebaseStorageService = firebaseStorageService;
    }

    // [Profile] 프로필 업로드
    @PostMapping("/upload/{userIdx}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, 
                                             @PathVariable("userIdx") int userIdx) {
        try {
            if (userIdx <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user index: " + userIdx);
            }

            String fileUrl = firebaseStorageService.uploadFile(file, userIdx);
            
            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // IOException에 대해 500번 상태 코드 반환
                    .body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // 예기치 않은 오류에 대해 400번 상태 코드 반환
                    .body("Invalid file: " + e.getMessage());
        }
    }
    
    // [Profile] 프로필 다운로드
    @GetMapping("/profile/{userIdx}")
    public ResponseEntity<?> downloadProfileImage(@PathVariable("userIdx") int userIdx) {
        try {
            try (InputStream image = firebaseStorageService.loadFile(userIdx)) {
                if (image != null) {
                    InputStreamResource resource = new InputStreamResource(image);
    
                    // 200 OK와 함께 이미지 반환
                    return ResponseEntity.ok()
                                         .contentType(MediaType.IMAGE_PNG)  // 이미지 파일이므로 MIME 타입 지정
                                         .body(resource);
                } else {
                    String errorMessage = "Profile image not found for user ID: " + userIdx;
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                         .body(errorMessage.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Error while fetching profile image for user ID: " + userIdx;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(errorMessage.getBytes());
        }
    }
    
}
