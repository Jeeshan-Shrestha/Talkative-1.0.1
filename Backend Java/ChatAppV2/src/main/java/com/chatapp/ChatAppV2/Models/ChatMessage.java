package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatMessage {

    private String sender;

    private String content;

    private MessageType type;

}
