package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileSearch {

    private String displayName;

    private String username;

    private Boolean isFollowing = false;

    private Integer followersCount = 0;

    private Integer followingCount = 0;

    private String Avatar;

    private String Bio;

    private Integer numberOfPosts = 0;

}
