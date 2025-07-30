package com.chatapp.ChatAppV2.Services;

import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    UserRepostory userRepo;

    public List<Post> getPostsFromUser(String username){
        Users user = userRepo.findByUsername(username);
        return user.getPosts();
    }

}
