package com.example.test.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

//@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("생략.json");

            // 서비스 계정 파일이 없을 경우 예외 처리
            if (serviceAccount == null) {
                throw new IllegalArgumentException("Service account file not found");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("생략.app") // Firebase Storage 사용 시 필요
                    .build();
    
            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }

            return FirebaseApp.getInstance();
        } catch (Exception e) {
            e.printStackTrace(); // 예외 출력
            throw new RuntimeException("Failed to initialize FirebaseApp", e);
        }

    }
}