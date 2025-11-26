package com.chatapp.ChatAppV2.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.MySelf;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class MySelfService {
    
    @Autowired
    private UserRepostory userRepo;

    public MySelf getMySelf(){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(currentUsername);
        MySelf myself = new MySelf();
        myself.setUsername(user.getUsername());
        myself.setDisplayName(user.getDisplayName());
        myself.setAvatar(user.getAvatar());
        return myself;
    }
    

}
