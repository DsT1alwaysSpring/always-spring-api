package com.example.test.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FRIENDS")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "F_IDX", nullable = false, unique = true)
    private int fIdx;

    @ManyToOne(fetch = FetchType.EAGER) // USER_IDX 외래키 관계 설정
    @JoinColumn(name = "USER_IDX", nullable = false)
    private User user; 

    @Column(name = "F_USER_IDX", nullable = false)
    private int fUser;

    @Column(name = "F_DATETIME", nullable = false)
    private LocalDate fDatatime;

}