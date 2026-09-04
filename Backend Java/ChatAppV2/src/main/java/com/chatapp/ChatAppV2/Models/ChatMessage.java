package com.chatapp.ChatAppV2.Models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatMessage {

  private LocalDateTime timestamp;

  private String sender;

  private String content;

  private MessageType type;

  private String receiver;

}
