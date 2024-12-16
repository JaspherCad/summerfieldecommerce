package com.example.hoteltest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String senderName;
	private String messageContent;
	private String message;
	private String date;
	private String status;
	private String receivername;
	
	
	
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", senderName=" + senderName + ", messageContent=" + messageContent + ", message="
				+ message + ", date=" + date + ", status=" + status + ", receivername=" + receivername + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public Message() {
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
