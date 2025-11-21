package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDTO {
    
    @Id
    private String id;

    private String username;

    private String displayName;

    private String postAccess; //public or private

    private String imageUrl;

    private int likes;

    private int numberOfComments;

    private List<Comment> comments;

    private String videoUrl;

    private String caption;

    private LocalDate postDate;

    private String avatar;

    private boolean liked;
    
}
