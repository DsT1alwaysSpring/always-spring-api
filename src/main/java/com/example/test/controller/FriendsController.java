package com.example.test.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Friends;
import com.example.test.model.User;
import com.example.test.repository.FriendsRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/friends")  
public class FriendsController {
    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private UserController userController;

    // ✅ 모든 사용자 친구 목록 조회 (GET /api/friends) 
    @GetMapping
    public List<Friends> getAllFriends() {
        return friendsRepository.findAll();
    }

    // ✅ 사용자 친구(대기/승인 모두) 조회 (GET /api/friends)
    @GetMapping("/all/{userIdx}")
    public ResponseEntity<List<Map<String, Object>>> getFriendsByUserId(@PathVariable int userIdx) {
        List<Friends> friends = friendsRepository.findByUser_UserIdx(userIdx); 
        if (friends.isEmpty()) {
            return ResponseEntity.notFound().build();  // 친구가 없으면 404 반환
        }

            List<Map<String, Object>> result = new ArrayList<>();
         
            for (Friends friend : friends) {
                Map<String, Object> friendInfo = new HashMap<>();
                friendInfo.put("friend", friend);
                
                User friendUser = userController.getUser(friend.getfUser());  
                if (friendUser != null) {
                    friendInfo.put("friendName", friendUser.getName());  
                } else {
                    continue;
                }

                result.add(friendInfo);
            }

        return ResponseEntity.ok(result);  
    }
    
    // ✅ 사용자 친구(승락) 조회 (GET /api/friends)
    @GetMapping("/{userIdx}")
    public ResponseEntity<List<Map<String, Object>>> getFriendsByUserIdAndStatus(@PathVariable int userIdx) {
        List<Friends> friends = friendsRepository.findByUser_UserIdxAndFriendRequestStatus(userIdx, "1"); 
        if (friends.isEmpty()) {
            return ResponseEntity.notFound().build();  // 친구가 없으면 404 반환
        }

        List<Map<String, Object>> result = new ArrayList<>();
         
        for (Friends friend : friends) {
            Map<String, Object> friendInfo = new HashMap<>();
            friendInfo.put("friend", friend);
            
            User friendUser = userController.getUser(friend.getfUser());  
            if (friendUser != null) {
                friendInfo.put("friendName", friendUser.getName());
            } else {
                continue;
            }

            result.add(friendInfo);
        }

        return ResponseEntity.ok(result); 
    }

    // ✅ 친구 추천 : 같은 지역에 사는 사용자를 친구로 추천
    @GetMapping("FriendRecommendation/{userIdx}")
    public ResponseEntity<List<User>> getFriendsByAddress(@PathVariable int userIdx) {
        List<User> friends = friendsRepository.findUsersByAddressOfLoggedInUser(userIdx); 
        if (friends.isEmpty()) {
            return ResponseEntity.notFound().build();  // 친구가 없으면 404 반환
        }

        return ResponseEntity.ok(friends);  // 친구 리스트가 있으면 200 OK와 함께 반환
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


    // ✅ [친구신청상태 대기 -> 승락] 본인 FriendRequestStatus 1로 변경과 동시에 (신청자: 친구, 승락자: 본인, FRS: 1) 생성  
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
        friendsRepository.save(existingFriend); // 기존 친구 정보 업데이트
    
        // 새로운 친구 관계 생성 (userIdx에 친구 id 등록, 본인의 id는 fUser로 설정)
        Friends newFriendRelation = new Friends();
        newFriendRelation.setUser(userController.getUser(friendIdx)); 
        newFriendRelation.setfUser(userIdx);  
        newFriendRelation.setFriendRequestStatus("1"); 
        newFriendRelation.setfDatatime(LocalDate.now());
        friendsRepository.save(newFriendRelation);
    
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
