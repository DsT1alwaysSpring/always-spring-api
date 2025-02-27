package com.example.test.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.Message;
import com.example.test.repository.MessageRepository;

@CrossOrigin(origins = "*")  // CORS 허용
@RestController
@RequestMapping("/api/message")  
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // Get all messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // Get messages by receiver
    // @GetMapping("/receiver/{receiver}")
    // public List<Message> getMessagesByReceiver(@PathVariable String receiver) {
    //     return messageRepository.findByReceiver(receiver);
    // }

    // // Get messages by sender
    // @GetMapping("/sender/{sender}")
    // public List<Message> getMessagesBySender(@PathVariable String sender) {
    //     return messageRepository.findBySender(sender);
    // }

    // Get a specific message by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        java.util.Optional<Message> message = messageRepository.findById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new message
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    // Update an existing message
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        if (!messageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        message.setId(id);
        Message updatedMessage = messageRepository.save(message);
        return ResponseEntity.ok(updatedMessage);
    }

    // Delete a message by ID
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    //     if (!messageRepository.existsById(id)) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     messageRepository.deleteById(id);
    //     return ResponseEntity.noContent().build();
    // }
}
