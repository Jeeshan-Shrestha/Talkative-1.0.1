package com.chatapp.ChatAppV2.Models;

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

    private Set<String> followers;

    private Integer followersCount;

    private Set<String> following;

    private Integer followingCount;

    private List<Post> posts;

}
