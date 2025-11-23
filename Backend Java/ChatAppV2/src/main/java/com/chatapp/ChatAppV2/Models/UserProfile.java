package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    
    private String username;

    private String displayName;

    private String Avatar;

    private String bio;

    private int followersCount;

    private int followingCount;

    private List<PostDTO> posts;

    private String coverPhoto;

    private LocalDate joinDate;

    private int numberOfPosts;

    private boolean isFollowing;

}
