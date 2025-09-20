package com.chatapp.ChatAppV2.Models;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProfileSearch {

    private String displayName;

    private String username;

    private boolean isFollowing;

    private int followersCount;

    private int followingCount;

    private String Avatar;

    private String Bio;

    private int numberOfPosts;

    public boolean isIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }


}
