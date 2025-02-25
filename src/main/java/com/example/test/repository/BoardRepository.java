package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.Board;

@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer>{
    
    List<Board> findBybIdx(Long bIdx);

    boolean existsBybIdx(int bIdx);
}
