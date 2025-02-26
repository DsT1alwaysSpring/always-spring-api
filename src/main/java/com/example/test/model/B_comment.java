package com.example.test.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "B_COMMENT")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class B_comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정(MySql에서 사용 가능)
    @Column(name="C_IDX", nullable=false, unique=true)    
    private int cIdx;

    @ManyToOne(fetch = FetchType.EAGER) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false)
    private User user; 

    @ManyToOne(fetch = FetchType.EAGER) // B_IDX 외래키 관계 설정
    @JoinColumn(name = "B_IDX", nullable = false)
    private Board board; 

    @Column(name = "C_DATE", nullable = false)
    private LocalDateTime cDate; 
    @Column(name = "COM_CONTENT", nullable = false)
    private String comContent;


    public B_comment(User user, Board board, String comContent) {
        this.user = user;
        this.board = board;
        this.comContent = comContent;
        this.cDate = LocalDateTime.now();
    }
    public int getcIdx() {
        return cIdx;
    }
    public void setcIdx(int cIdx) {
        this.cIdx = cIdx;
    }
    public LocalDateTime getcDate() {
        return cDate;
    }
    public void setcDate(LocalDateTime cDate) {
        this.cDate = cDate;
    }


    
}
