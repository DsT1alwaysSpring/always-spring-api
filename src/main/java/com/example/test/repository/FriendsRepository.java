package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.Friends;


@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    
}
