package com.chatapp.ChatAppV2.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private String postAccess; //public or private

    private String imageUrl;

    private Double likes;

    private List<String> comments;

    private String videoUrl;

    private String caption;
 
}
