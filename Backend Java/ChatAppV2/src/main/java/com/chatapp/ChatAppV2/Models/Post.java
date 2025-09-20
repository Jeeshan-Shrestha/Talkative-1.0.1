package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private String id;

    private String username;

    private String displayName;

    private String postAccess; //public or private

    private String imageUrl;

    private Integer likes;

    private List<String> comments;

    private String videoUrl;

    private String caption;

    private LocalDate postDate;

    private boolean isLiked;
 
}
