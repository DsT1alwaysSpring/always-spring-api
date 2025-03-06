package com.example.test.controller;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Message;
import com.example.test.model.User;
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

    // ✅ 본인이 속한 채팅방 chatRoomIdx 리스트 출력
    @GetMapping("/chatRoomData/{userIdx}")
    public ResponseEntity<List<Integer>> getAllChatRoomIdxByUserIdx(@PathVariable int userIdx) {
        // 메시지를 채팅방 idx로 조회하고, chatRoomIdx 기준으로 오름차순 정렬
        List<Integer> chatRoomIdxList = messageRepository.findChatRoomIdxByUserIdx(userIdx);
        
        if (chatRoomIdxList.isEmpty()) {
            return ResponseEntity.notFound().build(); // 메시지가 없으면 404
        }

        chatRoomIdxList.sort(Comparator.naturalOrder());
        return ResponseEntity.ok(chatRoomIdxList); // 메시지 있으면 200 OK와 함께 반환
    }

    // ✅ 본인이 속한 채팅방의 상대방 이름과 닉네임 출력
    @GetMapping("/chatRoomNickName/{userIdx}")
    public ResponseEntity<List<String>> getAllChatRoomIdxNickNameByUserIdx(@PathVariable int userIdx) {
        List<String> chatRoomIdxNickNameList = messageRepository.findFriendNicknamesByUserIdx(userIdx);
        
        if (chatRoomIdxNickNameList.isEmpty()) {
            return ResponseEntity.notFound().build(); // 메시지가 없으면 404
        }

        chatRoomIdxNickNameList.sort(Comparator.naturalOrder());
        return ResponseEntity.ok(chatRoomIdxNickNameList); // 메시지 있으면 200 OK와 함께 반환
    }

    // ✅ 채팅방으로 대화 조회
    @GetMapping("/chatRoom/{chatRoomIdx}")
    public ResponseEntity<List<Message>> getAllMessagesByChatRoom(@PathVariable int chatRoomIdx) {
        List<Message> messages = messageRepository.findByChatRoomIdx(chatRoomIdx, Sort.by(Sort.Order.asc("mDatetime")));
        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }

        return ResponseEntity.ok(messages);  
    }

    // ✅ 새로운 메시지 생성 & 채팅방 생성
    @PostMapping("createMessage/{messageContent}/{userIdx1}/{userIdx2}")
    public ResponseEntity<Message> createMessage(@PathVariable String messageContent,
        @PathVariable Integer userIdx1,
        @PathVariable Integer userIdx2) {

        // 함께 존재하는 채팅방이 있는지 확인
        Integer chatRoomNum = messageRepository.findChatRoomIdxByUserIdxs(userIdx1, userIdx2);
    
        // userIdx1과 userIdx2를 사용하여 User 객체를 조회
        User userData1 = userRepository.findById(userIdx1)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userIdx1));
        User userData2 = userRepository.findById(userIdx2)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userIdx2));
    
        // 메시지 객체 생성
        Message message = new Message();
    
        if (chatRoomNum != null && chatRoomNum > 0) {
            // 이미 존재하는 채팅방이 있을 경우 해당 채팅방 번호를 메시지에 설정
            message.setChatRoomIdx(chatRoomNum);  
            message.setUser(userData1); 
            message.setmContent(messageContent);
            message.setmDatetime(LocalDateTime.now());
            messageRepository.save(message);
        } else {
            // 채팅방이 존재하지 않으면 새로운 채팅방 생성
            Integer highestChatRoomIdx = messageRepository.findHighestChatRoomIdx(); // 가장 높은 chatRoomIdx 조회
            int newChatRoomIdx = (highestChatRoomIdx != null ? highestChatRoomIdx : 0) + 1; // 새로운 채팅방 번호
    
            // 새로운 채팅방에 해당하는 첫 번째 메시지 설정
            message.setChatRoomIdx(newChatRoomIdx);
            message.setUser(userData1);  
            message.setmContent(messageContent);
            message.setmDatetime(LocalDateTime.now());
            messageRepository.save(message); 
    
            // 두 번째 사용자에 해당하는 메시지 설정 (userIdx2)
            Message message2 = new Message();
            message2.setChatRoomIdx(newChatRoomIdx);
            message2.setUser(userData2);  
            message2.setmDatetime(LocalDateTime.now());
            messageRepository.save(message2);  
        }
    
        return ResponseEntity.ok(message);
    }
    
        
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

    // ✅ chatroomIdx로 채팅방 삭제
    @DeleteMapping("/deleteChatRoom/{chatRoomIdx}") 
    public ResponseEntity<String> deleteMessages(@PathVariable int chatRoomIdx) {
        messageRepository.deleteByChatRoomIdx(chatRoomIdx);
        return ResponseEntity.ok("Messages deleted successfully.");
    }

}