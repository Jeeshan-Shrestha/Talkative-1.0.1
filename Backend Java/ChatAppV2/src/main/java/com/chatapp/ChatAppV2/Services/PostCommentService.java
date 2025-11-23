package com.chatapp.ChatAppV2.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.Comment;
import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class PostCommentService {

    @Autowired
    private UserRepostory userRepo;

    public String postComment(Comment comment){

        comment.setCommentId(UUID.randomUUID().toString());
        String commentedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setCommentedBy(commentedBy);
        Users postOwner = userRepo.findByPostsId(comment.getPostId());
        if (postOwner == null){
            throw new UsernameNotFoundException("No user found");
        }
        List<Post> posts = postOwner.getPosts();
        if (posts == null){
            posts = new ArrayList<>();
        }
        Post commentedPost = posts.stream().filter(
            p -> p.getId().equals(comment.getPostId())).
            findFirst().orElse(null);

            if (commentedPost == null){
                throw new RuntimeException("No post found");
            }

        List<Comment> comments = commentedPost.getComments();
        if (comments == null){
            comments = new ArrayList<>();
            
        }
        int index = posts.indexOf(commentedPost);
        comments.add(comment);
        int numberOfComments = comments.size();
        commentedPost.setNumberOfComments(numberOfComments);
        commentedPost.setComments(comments);
        posts.set(index, commentedPost);
        postOwner.setPosts(posts);
        userRepo.save(postOwner);
        return "Successfully commented";
    }

    public List<Comment> getAllCommentsFromPost(String postId){
        Users user = userRepo.findByPostsId(postId);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);
        return post.getComments();
    }

    public String deleteComment(String commentId){
        Users user = userRepo.findByPostsCommentsCommentId(commentId);
        List<Post> posts = user.getPosts();
        for (Post post : posts){
            List<Comment> comments = post.getComments();
            for (Comment comment : comments){
                if (comment.getCommentId().equals(commentId)){
                    comments.remove(comments.indexOf(comment));
                    post.setComments(comments);
                    posts.set(posts.indexOf(post),post);
                    user.setPosts(posts);
                    userRepo.save(user);
                    return  "Deleted the comment";
                }
            }
        }
        return "Comment not found";
    }
    
}
