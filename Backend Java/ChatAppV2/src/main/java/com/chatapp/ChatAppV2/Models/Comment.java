package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    
    private String comment;

    private String postId;

    private boolean liked; 

    private String postOwnerUsername;

    private int numberOfLikes;

    private String commentedBy;
}
