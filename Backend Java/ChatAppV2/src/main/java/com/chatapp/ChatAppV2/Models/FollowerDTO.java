package com.chatapp.ChatAppV2.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerDTO {

    private String username;

    private String displayName;

    private String avatar;

    private boolean isFollowing;

    private boolean isOwnProfile;
}
