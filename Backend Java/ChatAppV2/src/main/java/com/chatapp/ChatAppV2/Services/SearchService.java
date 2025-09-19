package com.chatapp.ChatAppV2.Services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.ProfileSearch;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class SearchService {
    
    @Autowired
    private UserRepostory userRepo;

    public List<ProfileSearch> searchUser(String username){

        if (username.isEmpty()){
            List<Users> users = userRepo.findAll();
            Collections.shuffle(users);
            List<Users> filteredUser = users.subList(0, 4);
            List<ProfileSearch> randomUser = filteredUser.stream().map(u -> new ProfileSearch(
            u.getDisplayName(),
            u.getUsername(),
            u.getIsFollowing(),
            u.getFollowersCount(),
            u.getFollowingCount(),
            u.getAvatar(),
            u.getBio(),
            u.getNumberOfPosts())).collect(Collectors.toList());
        String searcher = SecurityContextHolder.getContext().getAuthentication().getName();
        Users searcherDetails = userRepo.findByUsername(searcher);
        for (ProfileSearch p : randomUser){
            if (searcherDetails.getFollowing()!= null&&searcherDetails.getFollowing().contains(p.getUsername())){
                p.setIsFollowing(true);
            }else{
                p.setIsFollowing(false);
            }
        }
        return randomUser;
    }

        List<Users> users = userRepo.findByUsernameStartingWithIgnoreCase(username);
        List<ProfileSearch> searchedUser = users.stream().map(u -> new ProfileSearch(
            u.getDisplayName(),
            u.getUsername(),
            u.getIsFollowing(),
            u.getFollowersCount(),
            u.getFollowingCount(),
            u.getAvatar(),
            u.getBio(),
            u.getNumberOfPosts())).collect(Collectors.toList());
        String searcher = SecurityContextHolder.getContext().getAuthentication().getName();
        Users searcherDetails = userRepo.findByUsername(searcher);
        for (ProfileSearch p : searchedUser){
            if (searcherDetails.getFollowing()!= null&&searcherDetails.getFollowing().contains(p.getUsername())){
                p.setIsFollowing(true);
            }else{
                p.setIsFollowing(false);
            }
        }
        return searchedUser;
    }

}
