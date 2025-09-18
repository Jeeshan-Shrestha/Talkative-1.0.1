package com.chatapp.ChatAppV2.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class PostService {

    @Autowired
    UserRepostory userRepo;

    public List<Post> getPostsFromUser(String username){
        Users user = userRepo.findByUsername(username);
        return user.getPosts();
    }

}
