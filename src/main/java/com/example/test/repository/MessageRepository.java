package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.test.model.Message;

import jakarta.transaction.Transactional;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {
    
    // 채팅방으로 메시지 조회
    List<Message> findByChatRoomIdx(int chatRoomIdx, Sort sort);

    // 두 사용자의 채팅방을 찾는 쿼리
    // Optional<Message> findByUser1_UserIdxAndUser2_UserIdx(int userIdx1, int userIdx2);

    // 가장 큰 chatRoomIdx를 가져오는 메서드
    Optional<Message> findTopByOrderByChatRoomIdxDesc(); 

    //본인이 속한 채팅방 idx 출력
    @Query("SELECT DISTINCT m.chatRoomIdx FROM Message m WHERE m.user.userIdx = :userIdx")
    List<Integer> findChatRoomIdxByUserIdx(@Param("userIdx") int userIdx);

    //본인이 속한 채팅방의 상대방의 이름과 닉네임 출력
    @Query("SELECT u.name, u.nickname FROM User u " +
    "WHERE u.userIdx IN (" +  // 상대방을 찾기 위해 User 테이블의 userIdx를 조회
    "    SELECT m.user.userIdx " +  // 해당 채팅방에서 상대방 userIdx를 가져옴
    "    FROM Message m " +
    "    WHERE m.chatRoomIdx IN (" +  // Message 테이블에서 본인이 속한 채팅방의 chatRoomIdx
    "        SELECT DISTINCT m.chatRoomIdx " +
    "        FROM Message m " +
    "        WHERE m.user.userIdx = :userIdx" +
    "    )" +
    "    AND m.user.userIdx != :userIdx" +  // 본인 제외, 즉 상대방
    ")")
    List<String> findFriendNicknamesByUserIdx(@Param("userIdx") int userIdx);



    // chatRoomIdx로 채팅방 삭제
    @Transactional
    void deleteByChatRoomIdx(int chatRoomIdx);

    // mIdx로 메시지 삭제
    @Transactional
    void deleteBymIdx(int chatRoomIdx);

}