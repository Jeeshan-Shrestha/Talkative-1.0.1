package com.chatapp.ChatAppV2.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
@Component
public class PostService {

    @Autowired
    UserRepostory userRepo;

    @Autowired
    private GridFsTemplate gridfs;

    public List<PostDTO> getPostsFromUser(String username){
        Users user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found");
        }
        List<Post> posts = user.getPosts(); 
        if (posts == null){
            posts = new ArrayList<>();
        }
        List<PostDTO> postDTO = convertPostsToDTO(posts);
        return postDTO;
    }

    public List<PostDTO> convertPostsToDTO(List<Post> posts) {
    String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

    return posts.stream().map(post -> {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setUsername(post.getUsername());
        dto.setDisplayName(post.getDisplayName());
        dto.setPostAccess(post.getPostAccess());
        dto.setImageUrl(post.getImageUrl());
        dto.setLikes(post.getLikes());
        dto.setNumberOfComments(post.getNumberOfComments());
        dto.setComments(post.getComments());
        dto.setVideoUrl(post.getVideoUrl());
        dto.setCaption(post.getCaption());
        dto.setPostDate(post.getPostDate());
        dto.setAvatar(post.getAvatar());
        dto.setLiked(post.getLikedBy() != null && post.getLikedBy().contains(currentUser));
        return dto;
    }).toList();
}

    public List<PostDTO> getSelfPost(){
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        Users selfUser = userRepo.findByUsername(self);
        List<Post> posts = selfUser.getPosts(); 
        if (posts == null){
            posts = new ArrayList<>();
        }
        List<PostDTO> postDTO = convertPostsToDTO(posts);
        return postDTO;
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
        post.setLikedBy(new HashSet<>());
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
        String likedBy = SecurityContextHolder.getContext().getAuthentication().getName();
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

        if (likedPost.getLikedBy() == null){
            likedPost.setLikedBy(new HashSet<>());
        }
        if (likedPost.getLikedBy().contains(likedBy)){
            likedPost.getLikedBy().remove(likedBy);
            likedPost.setLikes(likedPost.getLikes() -1);
            
            userRepo.save(likedUser);
            return "post unliked";
        }
        likedPost.getLikedBy().add(likedBy);
        likedPost.setLikes(likedPost.getLikes() + 1);

        userRepo.save(likedUser);
        return "post liked";

    }

    public String deletePost(String postId){
        Users user = userRepo.findByPostsId(postId);
        List<Post> posts = user.getPosts();
        for (Post post: posts){
            if (post.getId().equals(postId)){
                posts.remove(posts.indexOf(post));
                user.setNumberOfPosts(user.getNumberOfPosts() - 1);
                user.setPosts(posts);
                userRepo.save(user);
                return "Successfully deleted the post";
            }
        }
        return "Couldnt find the comment";
    }

}
