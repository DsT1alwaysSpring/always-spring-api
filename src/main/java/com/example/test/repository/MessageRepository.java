package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.test.model.Message;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer> {

}
