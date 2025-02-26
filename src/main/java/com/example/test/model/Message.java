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
    private int mIdx;

    @Column(name="USER_IDX", nullable=false)    
    private int userIdx;

    @Column(name="CHAT_ROOM_IDX", nullable=false)    
    private int chatRoomIdx;

    @Column(name = "M_CONTENT")
    private String mContent;

    @Column(name = "M_DATETIME")
    private LocalDateTime mDatetime;

    public Message(int userIdx, String m_content) {
        this.userIdx = userIdx;
        this.mContent = m_content;

        this.chatRoomIdx += 1;
        this.mDatetime = LocalDateTime.now();
    }

    public void setId(int userIdx) {
        this.userIdx = userIdx;
    }


    
}
