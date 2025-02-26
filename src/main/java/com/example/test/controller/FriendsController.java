package com.example.test.controller;
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

    // Get all friends
    @GetMapping
    public List<Friends> getAllFriends() {
        return friendsRepository.findAll();
    }

    // Get a friend by ID
    @GetMapping("/{id}")
    public ResponseEntity<Friends> getFriendById(@PathVariable int id) {
        Optional<Friends> friend = friendsRepository.findById(id);
        return friend.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new friend
    @PostMapping
    public ResponseEntity<Friends> createFriend(@RequestBody Friends friends) {
        Friends savedFriend = friendsRepository.save(friends);
        return ResponseEntity.ok(savedFriend);
    }

    // Update an existing friend
    @PutMapping("/{id}")
    public ResponseEntity<Friends> updateFriend(@PathVariable int id, @RequestBody Friends friend) {
        if (!friendsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        friend.setfIdx(id);
        Friends updatedFriend = friendsRepository.save(friend);
        return ResponseEntity.ok(updatedFriend);
    }

    // Delete a friend by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable int id) {
        if (!friendsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        friendsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
