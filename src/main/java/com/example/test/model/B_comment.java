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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name="C_IDX", nullable=false, unique=true)    
    private int cIdx;

    @ManyToOne(fetch = FetchType.LAZY) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false)
    private User user; 

    @ManyToOne(fetch = FetchType.LAZY) // B_IDX 외래키 관계 설정
    @JoinColumn(name = "B_IDX", nullable = false)
    private Board board; 

    @Column(name = "C_DATE", nullable = false)
    private LocalDateTime cDate; 
    @Column(name = "COM_CONTENT", nullable = false)
    private String comContent;

}
