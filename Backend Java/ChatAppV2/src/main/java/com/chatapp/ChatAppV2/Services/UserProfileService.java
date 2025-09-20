package com.chatapp.ChatAppV2.Services;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Exceptions.SelfFollowException;
import com.chatapp.ChatAppV2.Models.UserProfile;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class UserProfileService {

    @Autowired
    private UserRepostory userRepo;

    @Autowired
    private GridFsTemplate gridFsTemplate;


    public Set<String> getAllFollowers(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowers();
    }

    public Set<String> getAllFollowing(String username){
        Users userOnDb = userRepo.findByUsername(username);
        return userOnDb.getFollowing();
    }

    public String follow(String followedTo)throws Exception{
        String followedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        Users followedByUser = userRepo.findByUsername(followedBy);
        Users followedToUser = userRepo.findByUsername(followedTo);

        Set<String> following = followedByUser.getFollowing();
        if (following == null){
            following = new HashSet<>();
        }
        Set<String> followers = followedToUser.getFollowers();
        if (followers == null){
            followers = new HashSet<>();
        }
        if (following.contains(followedToUser.getUsername())){
            following.remove(followedTo);
            followers.remove(followedBy);

            followedByUser.setFollowing(following);
            followedByUser.setFollowingCount(followedByUser.getFollowingCount()==null? 0 : followedByUser.getFollowingCount() - 1);
            followedToUser.setFollowers(followers);
            followedToUser.setFollowersCount(followedToUser.getFollowersCount()==null? 0 : followedToUser.getFollowersCount() - 1);
            userRepo.save(followedByUser);
            userRepo.save(followedToUser);

            return followedBy + " unfollowed " + followedTo;
        }
        if (followedBy.equals(followedTo)){
            throw new SelfFollowException("You can't follow yourself :3");
        }
        following.add(followedTo);
        followers.add(followedBy);
        followedByUser.setFollowing(following);
        followedByUser.setFollowingCount(followedByUser.getFollowingCount()==null? 1 : followedByUser.getFollowingCount() + 1);
        followedToUser.setFollowers(followers);
        followedToUser.setFollowersCount(followedToUser.getFollowersCount()==null? 1 : followedToUser.getFollowersCount() + 1);

        userRepo.save(followedByUser);
        userRepo.save(followedToUser);
        return followedBy + " followed " + followedTo;
    }

    public UserProfile getUserProfile(String username)throws Exception{
        Users user = userRepo.findByUsername(username); 
        if (user == null){
            throw new UsernameNotFoundException("User doesnt exist");
        }
        return new UserProfile(user.getUsername(),
                                user.getDisplayName(),
                                user.getAvatar(),
                                user.getBio(),
                                user.getFollowers(),
                                user.getFollowersCount(),
                                user.getFollowing(),
                                user.getFollowingCount(),
                                user.getPosts(),
                                user.getCoverPhoto(),
                                user.getJoinDate(),
                                user.getNumberOfPosts()); 
    }

    public UserProfile getSelfProfile()throws Exception{
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(self); 
        if (user == null){
            throw new UsernameNotFoundException("User doesnt exist");
        }
        return new UserProfile(user.getUsername(),
                                user.getDisplayName(),
                                user.getAvatar(),
                                user.getBio(),
                                user.getFollowers(),
                                user.getFollowersCount(),
                                user.getFollowing(),
                                user.getFollowingCount(),
                                user.getPosts(),
                                user.getCoverPhoto(),
                                user.getJoinDate(),
                                user.getNumberOfPosts()
                                ); 
    }

    public String editProfile(MultipartFile avatar,String bio,String displayName,MultipartFile coverPhoto) throws IOException{
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(self); 
        ObjectId avatarId = gridFsTemplate.store(avatar.getInputStream(),avatar.getOriginalFilename(),avatar.getContentType());
        ObjectId coverPhotoId = gridFsTemplate.store(coverPhoto.getInputStream(),coverPhoto.getOriginalFilename(),coverPhoto.getContentType());
        user.setDisplayName(displayName);
        user.setAvatar("https://talkative-1-0-1-2.onrender.com/image/"+avatarId.toHexString());
        user.setCoverPhoto("https://talkative-1-0-1-2.onrender.com/image/"+coverPhotoId.toHexString());
        user.setBio(bio);
        userRepo.save(user);
        return "Edited Succesfully";
    }


}
