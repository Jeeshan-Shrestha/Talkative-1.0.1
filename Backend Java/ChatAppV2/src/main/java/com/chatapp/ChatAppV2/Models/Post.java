package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private String id;

    private String username;

    private String displayName;

    private String postAccess; //public or private

    private String imageUrl;

    private int likes;

    private List<String> comments;

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
