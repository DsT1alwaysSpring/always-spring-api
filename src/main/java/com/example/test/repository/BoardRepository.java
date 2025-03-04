package com.example.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test.model.Board;

import jakarta.transaction.Transactional;

@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer>{

    boolean existsBybIdx(int bIdx);

    // userIdx를 사용하여 Board를 찾는 메소드
    List<Board> findByUserUserIdx(int userIdx);
    //List<Board> findByUserUserIdx(Long userIdx);

    

    // 조회수 갱신
    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.views = b.views + 1 WHERE b.bIdx = :bIdx")
    void incrementViews(@Param("bIdx") int bIdx);

}
