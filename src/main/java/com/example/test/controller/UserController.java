package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // CORS 허용 (필요시 사용)
@RestController
@RequestMapping("/api/user")  // 기본 API 경로 설정
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public User getUser(int userIdx) {
        return userRepository.findByUserIdx(userIdx);
    }

    // ✅ 모든 회원 조회 (GET /api/user)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ 특정 회원 조회 (GET /api/user/{phone})
    @GetMapping("/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        List<User> users = userRepository.findByPhone(phone);
    
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users.get(0)); // 리스트가 비어 있지 않으면 첫 번째 사용자 반환
        } else {
            return ResponseEntity.notFound().build(); // 리스트가 비어 있으면 404 Not Found 반환
        }
    }

    // ✅ 회원 추가 (POST /api/user)
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // ✅ 회원 수정 (PUT /api/user/{id})
    @PutMapping("/{userIdx}")
    public User updateMember(@PathVariable int userIdx, @RequestBody User userDetails) {
        return userRepository.findById(userIdx).map(user -> {
            user.setName(userDetails.getName());
            user.setNickname(userDetails.getNickname());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setPassword(userDetails.getPassword());
            user.setProfile(userDetails.getProfile());
            user.setAppeal(userDetails.getAppeal());
            user.setKeyword(userDetails.getKeyword());
            user.setPurpose(userDetails.getPurpose());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("회원 찾을 수 없음"));
    }

    // ✅ 회원 삭제 (DELETE /api/user/{idx})
    @Transactional
    @DeleteMapping("/{userIdx}")
    public void deleteUser(@PathVariable int userIdx) {
        userRepository.deleteByuserIdx(userIdx);
    }

}
