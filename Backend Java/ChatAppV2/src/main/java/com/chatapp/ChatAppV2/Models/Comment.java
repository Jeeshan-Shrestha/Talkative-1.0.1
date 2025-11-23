package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String commentId;
    
    private String commentText;

    private String postId;

    private boolean liked; 

    private int numberOfLikes;

    private String commentedBy;
}
