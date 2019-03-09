package com.niit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.models.Chat;

@Controller
public class SockController {
private List<String> users=new ArrayList<String>();
private SimpMessagingTemplate simpMessagingTemplate;
@Autowired
public SockController(SimpMessagingTemplate simpMessagingTemplate){
	this.simpMessagingTemplate=simpMessagingTemplate;
}
@SubscribeMapping(value="/join/{username}")
public List<String> join(@DestinationVariable String username ){
	if(!users.contains(username))
		users.add(username);
	simpMessagingTemplate.convertAndSend("/topic/join",username);
	return users;
}

}



