package com.example.hoteltest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hoteltest.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderNameOrReceivername(String sender, String reciever);
    @Query("SELECT m FROM Message m WHERE (m.senderName = :sender AND m.receivername = :receiver) OR (m.senderName = :receiver AND m.receivername = :sender)")
    List<Message> findChatBetweenUsers(@Param("sender") String sender, @Param("receiver") String receiver);

    List<Message> findByReceivername(String publicChat); //null
    
    List<Message> findBySenderName(String name); //null
}