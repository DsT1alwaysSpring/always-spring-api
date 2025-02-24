package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // CORS 허용 (필요시 사용)
@RestController
@RequestMapping("/api/user")  // 기본 API 경로 설정
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
    


    // // ✅ 회원 추가 (POST /api/members)
    // @PostMapping
    // public User createMember(@RequestBody User user) {
    //     return userRepository.save(user);
    // }

    // ✅ 회원 수정 (PUT /api/members/{id})
    // @PutMapping("/{id}")
    // public User updateMember(@PathVariable String id, @RequestBody User memberDetails) {
    //     return userRepository.findById(id).map(member -> {
    //         member.setName(memberDetails.getName());
    //         member.setAge(memberDetails.getAge());
    //         member.setPhone(memberDetails.getPhone());
    //         member.setAddress(memberDetails.getAddress());
    //         member.setGender(memberDetails.getGender());
    //         member.setManager(memberDetails.getManager());
    //         member.setPassword(memberDetails.getPassword());
    //         return memberRepository.save(member);
    //     }).orElseThrow(() -> new RuntimeException("회원 찾을 수 없음"));
    // }

    // ✅ 회원 삭제 (DELETE /api/members/{id})
    // @DeleteMapping("/{id}")
    // public void deleteMember(@PathVariable String id) {
    //     userRepository.deleteById(id);
    // }

    // ✅ 로그인 기능 (GET /api/login)
    // @GetMapping("/login")
    // public Optional<User> login(@RequestParam String phone, @RequestParam String password) {
    //     return userRepository.findByMemberNumAndPassword(phone, password);
    // }
}
