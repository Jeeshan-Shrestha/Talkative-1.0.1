package com.chatapp.ChatAppV2.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class PostService {

    @Autowired
    UserRepostory userRepo;

    @Autowired
    private GridFsTemplate gridfs;

    public List<Post> getPostsFromUser(String username){
        Users user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found");
        }
        List<Post> post = user.getPosts(); 
        if (post == null){
            post = new ArrayList<>();
        }
        return post;
    }

    public List<Post> getSelfPost(){
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users selfUser = userRepo.findByUsername(self);
        List<Post> post = selfUser.getPosts(); 
        if (post == null){
            post = new ArrayList<>();
        }
        return post;
    }
    
    public void postThePost(String caption,MultipartFile file) throws IOException{

        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users selfUser = userRepo.findByUsername(self);
        ObjectId store = gridfs.store(file.getInputStream(),file.getOriginalFilename(),file.getContentType());

        Post post = new Post();
        post.setCaption(caption);
        post.setImageUrl("https://talkative-1-0-1-2.onrender.com/post/image/"+store.toHexString());
        post.setId(UUID.randomUUID().toString());
        post.setPostDate(LocalDate.now());
        post.setUsername(self);
        post.setDisplayName(selfUser.getDisplayName());
        post.setAvatar(selfUser.getAvatar());
        selfUser.setNumberOfPosts(selfUser.getNumberOfPosts() + 1);
        List<Post> postOnDb = selfUser.getPosts();
        if (postOnDb == null){
            postOnDb = new ArrayList<>();
        }
        postOnDb.add(post);
        selfUser.setPosts(postOnDb);
        userRepo.save(selfUser);  
    }

    public String likePost(String id, String likedUsername){
        Users likedUser = userRepo.findByUsername(likedUsername);
        List<Post> posts = likedUser.getPosts();
        Post likedPost = null;
        for (Post post : posts){
            if (post.getId().equals(id)){
                likedPost = post; 
                break;
            }
        }
        if (likedPost == null){
            throw new RuntimeException("No post found");
        }

        if (likedPost.getLiked()){
            likedPost.setLikes(likedPost.getLikes() -1);
            likedPost.setLiked(false);
            userRepo.save(likedUser);
            return "post unliked";
        }
        likedPost.setLikes(likedPost.getLikes() + 1);
        likedPost.setLiked(true);

        userRepo.save(likedUser);
        return "post liked";

    }

}
