package com.chatapp.ChatAppV2.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class FeedService {
    
    @Autowired
    private UserRepostory userRepo;

    @Autowired
    private PostService postService;

    public List<PostDTO> getHomeFeed(){
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
        return postList;
    }

}
