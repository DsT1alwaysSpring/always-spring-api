package com.example.test.repository;

import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 추가 기능: 휴대폰으로 회원 조회
    List<User> findByPhone(String phone);

    //멤버번호와 비밀번호가 일치하는 회원이 존재하는지 확인
    boolean existsByPhoneAndPassword(String phone, String password);
}
