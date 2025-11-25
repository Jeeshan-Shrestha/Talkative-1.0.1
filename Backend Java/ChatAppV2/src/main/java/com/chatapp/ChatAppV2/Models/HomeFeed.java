package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeFeed {
    
    @Id
    private String id;

    private String username;

    private String displayName;

    private String imageUrl;

    private int likes;

    private int numberOfComments;

    private String caption;

    private LocalDate postDate;

    private String avatar;

    private boolean liked;
}
