package com.mustr.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mustr.common.entity.Mess;

@RestController
public class ChatController extends ControllerBase{

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/toUser/{userId}")
	public void sendToUser(@PathVariable String userId) {
		simpMessagingTemplate.convertAndSendToUser(userId, "chat", new Mess("message"));
	}
}
