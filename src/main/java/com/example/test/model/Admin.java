package com.example.test.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ADMIN")  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name="ADMIN_IDX", nullable=false, unique=true)    
    private int admin_idx;

    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}
