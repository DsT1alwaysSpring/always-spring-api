package com.example.test.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "APP_USER")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name="USER_IDX", nullable=false, unique=true)    
    private int user_idx;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "BIRTH", nullable = false)
    private LocalDate birth;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "PROFILE")
    private String profile;

    @Column(name = "APPEAL")
    private String appeal;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "PURPOSE")
    private String purpose;
}
