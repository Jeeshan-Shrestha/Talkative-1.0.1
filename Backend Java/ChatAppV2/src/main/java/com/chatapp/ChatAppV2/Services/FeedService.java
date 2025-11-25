package com.chatapp.ChatAppV2.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.HomeFeed;
import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class FeedService {
    
    @Autowired
    private UserRepostory userRepo;

    @Autowired
    private PostService postService;

    public List<HomeFeed> getHomeFeed(){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(currentUserName);
        Set<String> following = currentUser.getFollowing();
        List<PostDTO> postList = new ArrayList<>();
        for (String followedUserName : following){
            Users followedUser = userRepo.findByUsername(followedUserName);
            if (followedUser.getPosts() != null){
            List<PostDTO> followedUserPosts = postService.convertPostsToDTO(followedUser.getPosts());
            postList.addAll(followedUserPosts);
            }
        }
        List<HomeFeed> homefeed = convertPostDTOtoHomeFeed(postList);
        return homefeed;
    }

    public List<HomeFeed> convertPostDTOtoHomeFeed(List<PostDTO> posts){
        List<HomeFeed> homeFeeds = posts.stream().map(post -> {
            HomeFeed homeFeed = new HomeFeed();
            homeFeed.setUsername(post.getUsername());
            homeFeed.setAvatar(post.getAvatar());
            homeFeed.setCaption(post.getCaption());
            homeFeed.setDisplayName(post.getDisplayName());
            homeFeed.setLikes(post.getLikes());
            homeFeed.setPostDate(post.getPostDate());
            homeFeed.setLiked(post.getLiked());
            homeFeed.setNumberOfComments(post.getNumberOfComments());
            homeFeed.setImageUrl(post.getImageUrl());
            homeFeed.setId(post.getId());
            return homeFeed;
        }).toList();
        return homeFeeds;
    }

}
