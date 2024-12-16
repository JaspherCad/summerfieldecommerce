package com.example.hoteltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.example.hoteltest.model.Message;
import com.example.hoteltest.repository.MessageRepository;

@RestController
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;	
	@Autowired
	MessageRepository messageRepository;
	
	@MessageMapping("/message") //  /app/message FOR FRONTEND TO ACCESS
	@SendTo("/chatroom/public") ///topic -> 		registry.enableSimpleBroker("/chatroom","/user");
	//hmm parang List of Array info lang like SSE... all msgs will hold here.. think of this as storage
	private Message recievePublicMessage(@Payload Message message) {//we recieve
		System.out.println(message);

        messageRepository.save(message);
		return message;
	}
	
	@MessageMapping("/private-message") //  FOR FRONTEND TO ACCESS    send message dynamically
	// -> 		registry.enableSimpleBroker("/chatroom","/user"); /user must be dynamically and not hard coded, so use SimpleMessagingTemplate
	private Message recievePrivateMessage(@Payload Message message) {  
		//user/:name/private??
		System.out.println("recievePrivateMessage: " + message);
        messageRepository.save(message);
		simpMessagingTemplate.convertAndSendToUser(message.getReceivername(), "/private", message); // -> automatically use the setUserDestinationPrefix("/user"); kasi sendTo""USER"" function
		return message;
	}
}
