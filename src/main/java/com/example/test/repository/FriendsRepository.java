package com.example.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test.model.Friends;



@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {

    // 사용자 id (userIdx)와 친구 id (friendIdx)를 이용해 친구 관계 조회
    @Query("SELECT f FROM Friends f WHERE f.user.id = :userIdx AND f.fUser = :fIdx")
    Optional<Friends> findByUserAndFUser(@Param("userIdx") int userIdx, @Param("fIdx") int fIdx);

    // 친구 요청 상태 업데이트하는 쿼리
    @Modifying
    @Query("UPDATE Friends f SET f.friendRequestStatus = '1' WHERE f.user.id = :userIdx AND f.fUser = :friendIdx")
    int updateFriendRequestStatus(@Param("userIdx") int userIdx, @Param("friendIdx") int friendIdx);
    
}
