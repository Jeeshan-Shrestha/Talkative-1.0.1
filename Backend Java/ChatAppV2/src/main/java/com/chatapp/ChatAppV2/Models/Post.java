package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Post {

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

    private boolean liked;

    private String avatar;

    public boolean getLiked(){
        return liked;
    }

    public void setLiked(boolean liked){
        this.liked = liked;
    }

    
}
