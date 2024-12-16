package com.example.hoteltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.authapi.UserService;
import com.example.hoteltest.model.Message;
import com.example.hoteltest.model.User;
import com.example.hoteltest.repository.MessageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageHistoryController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserService userService;

    @GetMapping("/public")
    public List<Message> getPublicMessages() {
        return messageRepository.findByReceivername("PUBLIC"); // Retrieve all public messages
    }

    
    @GetMapping("/sellers")
    public List<User> getAvailableSellersForChat() {
        return userService.getAllSellers();
    }
    
    
    @GetMapping("/private/{username}")
    public List<Message> getPrivateMessages(@PathVariable String username) {
        List<Message> messages = messageRepository.findBySenderNameOrReceivername(username, username);
        return messages;
    }
    
    @GetMapping("/private/{sender}/{receiver}")
    public List<Message> getChatBetweenUsers(@PathVariable String sender, @PathVariable String receiver) {
        return messageRepository.findChatBetweenUsers(sender, receiver);
    }
}
