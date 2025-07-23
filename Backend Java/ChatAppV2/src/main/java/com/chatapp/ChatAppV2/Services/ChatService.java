package com.chatapp.ChatAppV2.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.ChatMessage;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class ChatService {

    @Autowired
    UserRepostory userRepo;

    public Map<String,List<ChatMessage>> getMessageByUsername(String username){
        Users userOnDb = userRepo.findByUsername(username);
        Map<String,List<ChatMessage>> userChats = userOnDb.getChats();
        return userChats;
    }

    public ChatMessage saveMessage(String username, ChatMessage chat, String receiver) {
    Users userOnDb = userRepo.findByUsername(username);
    Map<String, List<ChatMessage>> userChats = userOnDb.getChats();

    if (userChats == null) {
        userChats = new HashMap<>(); // create new map if null
    }

    List<ChatMessage> chatWithReceiver = userChats.get(receiver);

    if (chatWithReceiver == null) {
        chatWithReceiver = new ArrayList<>();
    }

    chatWithReceiver.add(chat);
    userChats.put(receiver, chatWithReceiver);
    userOnDb.setChats(userChats); 

    userRepo.save(userOnDb);
    return chat;
}

    
}
