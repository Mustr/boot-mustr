package com.mustr.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
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
	
	@MessageMapping("/app/a")//处理客户端发来的目的地为 /app/a 的消息
	@SendTo("/topic/a") //转发给/topic/a的目的地
	//@SendToUser("/123/mess")//转发给指定用户123
	public String messageMethod(Object message) {
		
		return "hello world!";
	}
}
