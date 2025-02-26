package com.example.test.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.model.B_comment;

@Repository
public interface BCommentRepository extends JpaRepository<B_comment, Integer>{
    
    List<B_comment> findByBoard_BIdx(int boardIdx);

    boolean existsBycIdx(int cIdx);
}
