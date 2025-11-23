package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    
    private String commentId;
    
    private String commentText;

    private boolean liked; 

    private int numberOfLikes;

    private String commentedBy;

    private String avatar;

}
