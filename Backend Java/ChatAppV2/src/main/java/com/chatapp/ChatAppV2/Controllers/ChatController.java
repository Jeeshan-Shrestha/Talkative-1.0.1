package com.chatapp.ChatAppV2.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.ChatMessage;
import com.chatapp.ChatAppV2.Services.ChatService;



@RestController
@RequestMapping("/message")
public class ChatController {

    @Autowired
    ChatService chatService;
    
    @GetMapping("/{sender}/{receiver}")
    public ResponseEntity<?> getMessage(@PathVariable String sender,@PathVariable String receiver) {
        Map<String,List<ChatMessage>> chats = chatService.getMessageByUsername(sender,receiver);
        return ResponseEntity.ok().body(new BackendResponse(true, chats));
    }
}
