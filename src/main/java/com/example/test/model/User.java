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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")    // 인덱스 증가
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name="USER_IDX", nullable=false, unique=true)    
    private int userIdx;
    
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


    // JSON 데이터를 처리할 수 있는 생성자
    //@JsonCreator
    // public User(@JsonProperty("name")String name, String nickname, LocalDate birth, String phone, 
    //         String address, String password, String profile, String appeal, String keyword, String purpose) {
    //     this.name = name;
    //     this.nickname = nickname;
    //     this.birth = birth;
    //     this.phone = phone;
    //     this.address = address;
    //     this.password = password;

        // this.profile = (profile != null) ? profile : "미선택";
        // this.appeal = (appeal != null) ? appeal : "미선택";
        // this.keyword = (appeal != null) ? keyword : "미선택";
        // this.purpose = (appeal != null) ? purpose : "미선택";
    // }
    
    public int getuserIdx() {
        return userIdx;
    }
    
    public void setusreIdx(int userIdx) {
        this.userIdx = userIdx;
    }
    
}