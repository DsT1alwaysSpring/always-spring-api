package com.example.test.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MESSAGE")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name="M_IDX", nullable=false, unique=true)    
    private int m_idx;

    @Column(name="USER_IDX", nullable=false)    
    private int user_idx;

    @Column(name="CHAT_ROOM_IDX", nullable=false)    
    private int chat_room_idx;

    @Column(name = "M_CONTENT")
    private String m_content;

    @Column(name = "M_DATETIME")
    private LocalDateTime m_datetime;

}
