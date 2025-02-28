package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.test.model.Message;

import jakarta.transaction.Transactional;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {
    
    // 채팅방으로 메시지 조회
    List<Message> findByChatRoomIdx(int chatRoomIdx, Sort sort);

    // 두 사용자의 채팅방을 찾는 쿼리
    //Optional<Message> findByUser1_UserIdxAndUser2_UserIdx(int userIdx1, int userIdx2);

    // 가장 큰 chatRoomIdx를 가져오는 메서드
    Optional<Message> findTopByOrderByChatRoomIdxDesc(); 

    // chatRoomIdx로 채팅방 삭제
    @Transactional
    void deleteByChatRoomIdx(int chatRoomIdx);

    // mIdx로 메시지 삭제
    @Transactional
    void deleteBymIdx(int chatRoomIdx);

}