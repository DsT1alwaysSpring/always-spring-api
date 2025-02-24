package com.example.test.controller;

import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api")  //  로그인 API를 별도의 컨트롤러로 분리
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    //  로그인 기능 (GET /api/login)
    @GetMapping("/login")
    public boolean login(@RequestParam String phone, @RequestParam String password) {
    return userRepository.existsByPhoneAndPassword(phone, password);
    }

}
