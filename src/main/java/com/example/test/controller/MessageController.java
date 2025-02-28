package com.example.test.controller;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Message;
import com.example.test.repository.MessageRepository;
import com.example.test.repository.UserRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/message")  
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ 채팅방으로 대화 조회
    @GetMapping("/chatRoom/{chatRoomIdx}")
    public ResponseEntity<List<Message>> getAllMessagesByChatRoom(@PathVariable int chatRoomIdx) {
        List<Message> messages = messageRepository.findByChatRoomIdx(chatRoomIdx, Sort.by(Sort.Order.asc("mDatetime")));
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity.ok(messages);  
    }

    // 새로운 메시지 생성 -> 친구 idx와 본인Idx 가져와서, 채팅방 번호 통일시켜야 함[수정 줌]
    // @PostMapping
    // public ResponseEntity<Message> createMessage(@RequestBody Message message, int userIdx1, int userIdx2) {
        
    //     // 함께 존재하는 채팅방이 있는지 확인

    //     // userIdx1의 채팅방idx 조회 
    //     // userIdx2의 채팅방idx 조회

    //     // 해당 리스트끼리 일치하는 값 있으면, exist(boolean) 값 true -> if(!exist){return 0;} 
    //     //else{동시에 같은 useridx1과 useridx2가 chatroomidx(현재 가장 높은 값 조회 + 1)를 가지도록 설정 후 message 생성}
    //     boolean exist = messageRepository.findByUser1_UserIdxAndUser2_UserIdx(userIdx1, userIdx2);
    //     if(exist)

    //     Message savedMessage = messageRepository.save(message);
    //     return ResponseEntity.ok(savedMessage);
    // }

    // ✅ mIdx로 구체적인 메세지 가져오기
    @GetMapping("/{mIdx}")
    public ResponseEntity<Message> getMessageById(@PathVariable int mIdx) {
        Optional<Message> message = messageRepository.findById(mIdx);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // ✅ mIdx로 삭제
    @DeleteMapping("/deleteMIdx/{mIdx}")
    public ResponseEntity<String> deleteMessage(@PathVariable int mIdx) {
        if (!messageRepository.existsById(mIdx)) {
            return ResponseEntity.notFound().build();
        }

        messageRepository.deleteById(mIdx);
        return ResponseEntity.ok("Messages deleted successfully.");
    }

    // ✅ chatroomIdx로 채팅방 색제
    @DeleteMapping("/deleteChatRoom/{chatRoomIdx}") 
    public ResponseEntity<String> deleteMessages(@PathVariable int chatRoomIdx) {
        messageRepository.deleteByChatRoomIdx(chatRoomIdx);
        return ResponseEntity.ok("Messages deleted successfully.");
    }

}
