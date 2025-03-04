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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_seq")   
    @SequenceGenerator(name = "m_seq", sequenceName = "M_SEQ", allocationSize = 1)
    @Column(name="M_IDX", nullable=false, unique=true)    
    private int mIdx;

    @ManyToOne(fetch = FetchType.EAGER) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false) 
    private User user;

    @Column(name="CHAT_ROOM_IDX", nullable=false)    
    private int chatRoomIdx;

    @Column(name = "M_CONTENT")
    private String mContent;

    @Column(name = "M_DATETIME")
    private LocalDateTime mDatetime;

    public Message(User user, String m_content) {
        this.user = user;
        this.mContent = m_content;

        this.chatRoomIdx += 1;
        this.mDatetime = LocalDateTime.now();
    }

    public void setId(User user) {
        this.user = user;
    }

    public int getmIdx() {
        return mIdx;
    }

    public void setmIdx(int mIdx) {
        this.mIdx = mIdx;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public LocalDateTime getmDatetime() {
        return mDatetime;
    }

    public void setmDatetime(LocalDateTime mDatetime) {
        this.mDatetime = mDatetime;
    }
    
    
}
