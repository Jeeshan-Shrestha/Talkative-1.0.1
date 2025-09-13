package com.chatapp.ChatAppV2.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Exceptions.AlreadyFollowedException;
import com.chatapp.ChatAppV2.Exceptions.AlreadyUnfollowedException;
import com.chatapp.ChatAppV2.Exceptions.SelfFollowException;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class UserProfileService {

    @Autowired
    UserRepostory userRepo;

    public Set<String> getAllFollowers(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowers();
    }

    public Set<String> getAllFollowing(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowing();
    }

    public String follow(String followedBy,String followedTo)throws Exception{
        Users followedByUser = userRepo.findByUsername(followedBy);
        Users followedToUser = userRepo.findByUsername(followedTo);

        Set<String> following = followedByUser.getFollowing();
        if (following == null){
            following = new HashSet<>();
        }
        if (following.contains(followedToUser.getUsername())){
            throw new AlreadyFollowedException("you have already followed this person");
        }
        if (followedByUser.getUsername().equals(followedToUser.getUsername())){
            throw new SelfFollowException("You can't follow yourself :3");
        }
        following.add(followedToUser.getUsername());
        Set<String> followers = followedToUser.getFollowers();
        if (followers == null){
            followers = new HashSet<>();
        }
        followers.add(followedByUser.getUsername());

        followedByUser.setFollowing(following);
        followedByUser.setFollowingCount(followedByUser.getFollowingCount()==null? 1 : followedByUser.getFollowersCount() + 1);
        followedToUser.setFollowers(followers);
        followedToUser.setFollowersCount(followedToUser.getFollowersCount()==null? 1 : followedToUser.getFollowersCount() + 1);

        userRepo.save(followedByUser);
        userRepo.save(followedToUser);
        return followedBy + " followed " + followedTo;
    }

    public String unfollow(String unfollowedBy,String unfollowedTo)throws Exception{
        Users unfollowedByUser = userRepo.findByUsername(unfollowedBy);
        Users unfollowedToUser = userRepo.findByUsername(unfollowedTo);

        if (!unfollowedByUser.getFollowing().contains(unfollowedTo)){
            throw new AlreadyUnfollowedException("Follow the user first to unfollow them");
        }
        if (unfollowedByUser.getUsername().equals(unfollowedToUser.getUsername())){
            throw new SelfFollowException("You can't unfollow yourself :3");
        }

        Set<String> following = unfollowedByUser.getFollowing();
        if (following == null){
            following = new HashSet<>();
        }
        following.remove(unfollowedToUser.getUsername());
        Set<String> followers = unfollowedToUser.getFollowers();
        if (followers == null){
            followers = new HashSet<>();
        }
        followers.remove(unfollowedByUser.getUsername());

        unfollowedByUser.setFollowing(following);
        unfollowedByUser.setFollowingCount(unfollowedByUser.getFollowingCount()==null? 0 : unfollowedByUser.getFollowingCount() - 1);
        unfollowedToUser.setFollowers(followers);
        unfollowedToUser.setFollowersCount(unfollowedToUser.getFollowersCount()==null? 0 : unfollowedToUser.getFollowersCount() - 1);

        userRepo.save(unfollowedByUser);
        userRepo.save(unfollowedToUser);
        return unfollowedBy + " unfollowed " + unfollowedTo;
    }

}
