package com.chatapp.ChatAppV2.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Exceptions.SelfFollowException;
import com.chatapp.ChatAppV2.Models.FollowerDTO;
import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Models.UserProfile;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class UserProfileService {

    @Autowired
    private UserRepostory userRepo;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private PostService postService;


    public Set<FollowerDTO> getAllFollowers(String username){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(currentUserName);
        Users userOnDb = userRepo.findByUsername(username);
        Set<String> followers = userOnDb.getFollowers();
        Set<FollowerDTO> followerDTOs = new HashSet<>();
        if (followers != null){
            for (String follower : followers){
                FollowerDTO followerDTO = new FollowerDTO();
                Users user = userRepo.findByUsername(follower);
                followerDTO.setUsername(user.getUsername());
                followerDTO.setDisplayName(user.getDisplayName());
                followerDTO.setAvatar(user.getAvatar());
                followerDTO.setFollowing(currentUser.getFollowing().contains(follower));
                followerDTO.setOwnProfile(follower.equals(currentUserName));
                followerDTOs.add(followerDTO);
            }
        }
        return followerDTOs;
    }

    public Set<FollowerDTO> getAllFollowing(String username){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(currentUserName);
        Users userOnDb = userRepo.findByUsername(username);
        Set<String> following = userOnDb.getFollowing();
        Set<FollowerDTO> followerDTOs = new HashSet<>();
        for (String follower : following){
            FollowerDTO followerDTO = new FollowerDTO();
            Users user = userRepo.findByUsername(follower);
            followerDTO.setUsername(user.getUsername());
            followerDTO.setDisplayName(user.getDisplayName());
            followerDTO.setAvatar(user.getAvatar());
            followerDTO.setFollowing(currentUser.getFollowing().contains(follower));
            followerDTO.setOwnProfile(follower.equals(currentUserName));
            followerDTOs.add(followerDTO);
        }
        return followerDTOs;
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
            followedByUser.setFollowingCount(followedByUser.getFollowingCount() - 1);
            followedToUser.setFollowers(followers);
            followedToUser.setFollowersCount(followedToUser.getFollowersCount() - 1);
            followedToUser.setIsFollowing(false);
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
        followedByUser.setFollowingCount(followedByUser.getFollowingCount() + 1);
        followedToUser.setFollowers(followers);
        followedToUser.setFollowersCount(followedToUser.getFollowersCount() + 1);
        followedToUser.setIsFollowing(true);
        userRepo.save(followedByUser);
        userRepo.save(followedToUser);
        return followedBy + " followed " + followedTo;
    }

    public UserProfile getUserProfile(String username)throws Exception{
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(username); 
        if (user == null){
            throw new UsernameNotFoundException("User doesnt exist");
        }
        List<Post> posts = user.getPosts();
        if (posts == null) {
            posts = new ArrayList<>();
        }
        boolean isFollowing = false;
        List<PostDTO> dto = postService.convertPostsToDTO(posts);
        if (user.getFollowers().contains(currentUser)){
            isFollowing = true;
        }else{
            isFollowing = false;
        }
        return new UserProfile(user.getUsername(),
                                user.getDisplayName(),
                                user.getAvatar(),
                                user.getBio(),
                                user.getFollowersCount(),
                                user.getFollowingCount(),
                                dto,
                                user.getCoverPhoto(),
                                user.getJoinDate(),
                                user.getNumberOfPosts(),
                                isFollowing); 
    }

    public UserProfile getSelfProfile()throws Exception{
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(self); 
        if (user == null){
            throw new UsernameNotFoundException("User doesnt exist");
        }

        List<Post> posts = user.getPosts();
        if (posts == null) {
            posts = new ArrayList<>();
        }

        List<PostDTO> dto = postService.convertPostsToDTO(posts);
        return new UserProfile(user.getUsername(),
                                user.getDisplayName(),
                                user.getAvatar(),
                                user.getBio(),
                                user.getFollowersCount(),
                                user.getFollowingCount(),
                                dto,
                                user.getCoverPhoto(),
                                user.getJoinDate(),
                                user.getNumberOfPosts(),
                                user.getIsFollowing()
                            ); 
    }

    public String editProfile(MultipartFile avatar,String bio,String displayName,MultipartFile coverPhoto) throws IOException{
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepo.findByUsername(self); 
        if (avatar != null && coverPhoto != null){
            ObjectId avatarId = gridFsTemplate.store(avatar.getInputStream(),avatar.getOriginalFilename(),avatar.getContentType());
            ObjectId coverPhotoId = gridFsTemplate.store(coverPhoto.getInputStream(),coverPhoto.getOriginalFilename(),coverPhoto.getContentType());
            user.setAvatar("https://talkative-1-0-1-2.onrender.com/post/image/"+avatarId.toHexString());
            user.setCoverPhoto("https://talkative-1-0-1-2.onrender.com/post/image/"+coverPhotoId.toHexString());  
        }
        if (avatar == null && coverPhoto != null){
            ObjectId coverPhotoId = gridFsTemplate.store(coverPhoto.getInputStream(),coverPhoto.getOriginalFilename(),coverPhoto.getContentType());  
            user.setCoverPhoto("https://talkative-1-0-1-2.onrender.com/post/image/"+coverPhotoId.toHexString());  
        }

        if (avatar != null && coverPhoto == null){
            ObjectId avatarId = gridFsTemplate.store(avatar.getInputStream(),avatar.getOriginalFilename(),avatar.getContentType());
            user.setAvatar("https://talkative-1-0-1-2.onrender.com/post/image/"+avatarId.toHexString());
        }
        user.setBio(bio);
        user.setDisplayName(displayName);
       List<Post> posts = user.getPosts();
       if (posts != null && avatar != null){
       for (Post p : posts){
            ObjectId avatarId = gridFsTemplate.store(avatar.getInputStream(),avatar.getOriginalFilename(),avatar.getContentType());
            p.setDisplayName(displayName);
            p.setAvatar("https://talkative-1-0-1-2.onrender.com/post/image/"+avatarId.toHexString());
       }
    }
        userRepo.save(user);
        return "Edited Succesfully";
    }


}
