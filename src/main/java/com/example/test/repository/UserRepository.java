package com.example.test.repository;

import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //휴대폰으로 회원 조회
    List<User> findByPhone(String phone);

    //idx로 사용자 정보 조회
    User findByUserIdx(int userIdx);

    //휴대폰과 비밀번호로 회원 데이터 조회
    Optional<User> findByPhoneAndPassword(String phone, String password);

    //유저번호와 비밀번호가 일치하는 회원이 존재하는지 확인
    boolean existsByPhoneAndPassword(String phone, String password);

    //사용자 idx로 유저 탈퇴
    void deleteByuserIdx(int userIdx);

}
