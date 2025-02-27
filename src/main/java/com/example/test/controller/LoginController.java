package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api")  //  로그인 API를 별도의 컨트롤러로 분리
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // 로그인 검증 (GET /api/validateLogin)
    @GetMapping("/validateLogin")
    public boolean validateLogin(@RequestParam String phone, @RequestParam String password) {
        return userRepository.existsByPhoneAndPassword(phone, password);
    }

    // 로그인 후 사용자 데이터 반환 (GET /api/login)
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String phone, @RequestParam String password) {
        Optional<User> user = userRepository.findByPhoneAndPassword(phone, password);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            // 사용자가 없으면 401 Unauthorized 응답을 반환(return 1)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
