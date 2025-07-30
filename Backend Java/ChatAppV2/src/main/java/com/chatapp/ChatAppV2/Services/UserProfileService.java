package com.chatapp.ChatAppV2.Services;

import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    @Autowired
    UserRepostory userRepo;

    public List<String> getAllFollowers(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowers();
    }

    public List<String> getAllFollowing(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowing();
    }

    public String follow(String followedBy,String followedTo){
        Users followedByUser = userRepo.findByUsername(followedBy);
        Users followedToUser = userRepo.findByUsername(followedTo);
        followedByUser.setFollowing(List.of(followedToUser.getUsername()));
        followedToUser.setFollowers(List.of(followedByUser.getUsername()));
        userRepo.save(followedByUser);
        userRepo.save(followedToUser);
        return followedBy + " followed " + followedTo;
    }

}
