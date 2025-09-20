package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserProfile {
    
    private String username;

    private String displayName;

    private String Avatar;

    private String bio;

    private Set<String> followers;

    private Integer followersCount = 0;

    private Set<String> following;

    private Integer followingCount = 0;

    private List<Post> posts;

    private String coverPhoto;

    private LocalDate joinDate;

    private Integer numberOfPosts = 0;

}
