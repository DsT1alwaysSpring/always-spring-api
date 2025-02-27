package com.example.test.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Friends;
import com.example.test.repository.FriendsRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/friends")  
public class FriendsController {
    @Autowired
    private FriendsRepository friendsRepository;

    // ✅ 모든 사용자 친구 목록 조회 (GET /api/friends) 
    @GetMapping
    public List<Friends> getAllFriends() {
        return friendsRepository.findAll();
    }

    // 사용자 친구 조회 (GET /api/friends)
    @GetMapping("/{id}")
    public ResponseEntity<Friends> getFriendById(@PathVariable int userIdx) {
        Optional<Friends> friend = friendsRepository.findById(userIdx);
        return friend.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 친구 생성 (POST /api/friends)
    @PostMapping
    public ResponseEntity<Friends> createFriend(@RequestBody Friends friends) {
        friends.setFriendRequestStatus("0");
        Friends savedFriend = friendsRepository.save(friends);
        return ResponseEntity.ok(savedFriend);
    }

    // ✅ [대기친구 승인 시 사용] 친구 설정 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Friends> updateFriendRequestStatus(@PathVariable int id) {
        Optional<Friends> optionalFriend = friendsRepository.findById(id);
    
        if (!optionalFriend.isPresent()) {
            return ResponseEntity.notFound().build(); //404 return
        }
    
        Friends existingFriend = optionalFriend.get();
        existingFriend.setFriendRequestStatus("1");
        Friends updatedFriend = friendsRepository.save(existingFriend);
    
        // 업데이트된 친구 정보를 200 OK 응답으로 반환
        return ResponseEntity.ok(updatedFriend); 
    }


    //수정 중
    @PutMapping("/{userIdx}/{friendIdx}")
    public ResponseEntity<Friends> updateFriendRequestStatus(
            @PathVariable int userIdx,        // 본인 idx
            @PathVariable int friendIdx     // 친구 idx
    ) {
        // 해당 친구 관계가 존재하는지 확인
        Optional<Friends> optionalFriend = friendsRepository.findByUserAndFUser(userIdx, friendIdx);
    
        if (!optionalFriend.isPresent()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        Friends existingFriend = optionalFriend.get();
    
        existingFriend.setFriendRequestStatus("1");
    
        // 친구 요청 상태를 업데이트
        friendsRepository.save(existingFriend); // 기존 친구 정보 업데이트
    
        // 새로운 친구 관계 생성 (userIdx에 친구 id 등록, 본인의 id는 fUser로 설정)
        Friends newFriendRelation = new Friends();
        newFriendRelation.setUser(existingFriend.getUser()); 
        newFriendRelation.setfUser(userIdx);  
        newFriendRelation.setFriendRequestStatus("1"); 
        newFriendRelation.setfDatatime(LocalDate.now());
        friendsRepository.save(newFriendRelation);
    
        // 업데이트된 친구 정보 반환
        return ResponseEntity.ok(newFriendRelation);  // 새로 생성된 친구 관계 반환
    }
    


    // ✅ fIdx로 친구 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable int id) {
        if (!friendsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        friendsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
