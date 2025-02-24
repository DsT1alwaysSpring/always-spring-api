package com.example.test.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BOARD")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "B_IDX", nullable = false, unique = true)
    private int bIdx; // Java에서는 카멜 케이스 사용

    @ManyToOne(fetch = FetchType.LAZY) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false)
    private User user; // User 엔티티와의 관계

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "B_DATETIME", nullable = false)
    private LocalDateTime bDatetime; 

    @Column(name = "VIEWS", nullable = false)
    private int views;

    @Column(name = "B_S_PERIOD")
    private LocalDateTime bSPeriod; 

    @Column(name = "B_E_PERIOD")
    private LocalDateTime bEPeriod; 

    @Enumerated(EnumType.STRING) // B_STATE를 Enum으로 처리
    @Column(name = "B_STATE")
    private BoardState bState; 

    // B_STATE를 Enum으로 정의
    public enum BoardState {
    PROGRESS("진행중"),
    CLOSED("마감");

    private String description;

    BoardState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
}

