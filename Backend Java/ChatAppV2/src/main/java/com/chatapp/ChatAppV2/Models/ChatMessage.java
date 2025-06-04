package com.chatapp.ChatAppV2.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    
    private String sender;

    private String message;

    private MessageType type;

}
