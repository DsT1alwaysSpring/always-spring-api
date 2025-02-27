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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")    // 인덱스 증가
    @SequenceGenerator(name = "board_seq", sequenceName = "BOARD_SEQ", allocationSize = 1)
    @Column(name = "B_IDX", nullable = false, unique = true)
    private int bIdx;

    @ManyToOne(fetch = FetchType.EAGER) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false)
    private User user; 

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "B_DATETIME", nullable = false)
    private LocalDateTime bDatetime; 

    @Column(name = "VIEWS")
    private Integer views;

    @Column(name = "B_S_PERIOD")
    private LocalDateTime bSPeriod; 

    @Column(name = "B_E_PERIOD")
    private LocalDateTime bEPeriod; 

    @Column(name = "B_STATE")
    private String bState; 


    public int getbIdx() {
        return bIdx;
    }

    public void setbIdx(int bIdx) {
        this.bIdx = bIdx;
    }

    public LocalDateTime getbDatetime() {
        return bDatetime;
    }

    public void setbDatetime(LocalDateTime bDatetime) {
        this.bDatetime = bDatetime;
    }

    public LocalDateTime getbSPeriod() {
        return bSPeriod;
    }

    public void setbSPeriod(LocalDateTime bSPeriod) {
        this.bSPeriod = bSPeriod;
    }

    public LocalDateTime getbEPeriod() {
        return bEPeriod;
    }

    public void setbEPeriod(LocalDateTime bEPeriod) {
        this.bEPeriod = bEPeriod;
    }

    public String getbState() {
        return bState;
    }

    public void setbState(String bState) {
        this.bState = bState;
    }
    
}

