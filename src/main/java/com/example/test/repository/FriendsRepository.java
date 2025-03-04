package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test.model.Friends;
import com.example.test.model.User;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {

    // 친구 리스트 조회
    List<Friends> findByUser_UserIdx(int userIdx);

    // userIdx가 일치하고, friendRequestStatus가 1인 친구들 찾기
    List<Friends> findByUser_UserIdxAndFriendRequestStatus(int userIdx, String friendRequestStatus);

    // 사용자 id (userIdx)와 친구 id (friendIdx)를 이용해 친구 관계 조회
    @Query("SELECT f FROM Friends f WHERE f.user.id = :userIdx AND f.fUser = :fIdx")
    Optional<Friends> findByUserAndFUser(@Param("userIdx") int userIdx, @Param("fIdx") int fIdx);

    // 친구 요청 상태 업데이트하는 쿼리
    @Modifying
    @Query("UPDATE Friends f SET f.friendRequestStatus = '1' WHERE f.user.id = :userIdx AND f.fUser = :friendIdx")
    int updateFriendRequestStatus(@Param("userIdx") int userIdx, @Param("friendIdx") int friendIdx);

    // 같은 동네에 사는 사용자 친구 추천
    @Query("SELECT u FROM User u WHERE u.address = (SELECT u2.address FROM User u2 WHERE u2.userIdx = :userIdx) AND u.userIdx != :userIdx")
    List<User> findUsersByAddressOfLoggedInUser(@Param("userIdx") int userIdx);    

}
