package com.chatapp.ChatAppV2.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.Comment;
import com.chatapp.ChatAppV2.Models.CommentDTO;
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
        Users currentUser = userRepo.findByUsername(commentedBy);
        comment.setCommentedBy(commentedBy);
        comment.setUserId(currentUser.getId());
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

    public List<CommentDTO> getAllCommentsFromPost(String postId){
        Users user = userRepo.findByPostsId(postId);
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId)).findFirst().orElse(null);

        List<Comment> comments = post.getComments();
        List<CommentDTO> commentDTO = convertCommentToCommentDTO(comments);
        return commentDTO;
    }

    public List<CommentDTO> convertCommentToCommentDTO(List<Comment> comments){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments){
            Users user = userRepo.findById(comment.getUserId()).orElse(null);
            CommentDTO dto = new CommentDTO();
            dto.setCommentId(comment.getCommentId());
            dto.setCommentedBy(comment.getCommentedBy());
            dto.setCommentText(comment.getCommentText());
            dto.setAvatar(user != null ? user.getAvatar() : null);
            dto.setOwnProfile(comment.getCommentedBy().equals(currentUsername));
            dto.setNumberOfLikes(comment.getNumberOfLikes());
            dto.setLiked(comment.getLikedBy() != null && comment.getLikedBy().contains(currentUsername));
            commentDTOs.add(dto);
        }
        return commentDTOs;
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
                    post.setNumberOfComments(post.getNumberOfComments() -1);
                    posts.set(posts.indexOf(post),post);
                    user.setPosts(posts);
                    userRepo.save(user);
                    return  "Deleted the comment";
                }
            }
        }
        return "Comment not found";
    }

    public String likeComment(String commentId){
        Users user = userRepo.findByPostsCommentsCommentId(commentId);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user == null)
            throw new UsernameNotFoundException("No user found");
        List<Post> posts = user.getPosts();
        for (Post post : posts) {
            List<Comment> comments = post.getComments();
            for (Comment c : comments) {
                Set<String> likedBy = c.getLikedBy();
                if (likedBy == null){
                    likedBy = new HashSet<>();
                }
                if (c.getCommentId().equals(commentId)) {
                    if (likedBy.contains(currentUsername)){
                        c.setNumberOfLikes(c.getNumberOfLikes() - 1);
                        likedBy.remove(currentUsername);
                        c.setLikedBy(likedBy);
                        comments.set(comments.indexOf(c), c);
                        posts.set(posts.indexOf(post), post);
                        user.setPosts(posts);
                        userRepo.save(user);
                        return "Unliked comment";
                    }else{
                       c.setNumberOfLikes(c.getNumberOfLikes() + 1);
                       likedBy.add(currentUsername);
                        c.setLikedBy(likedBy);
                        comments.set(comments.indexOf(c), c);
                        posts.set(posts.indexOf(post), post);
                        user.setPosts(posts);
                        userRepo.save(user); 
                        return "Liked comment";
                    }
                }
            }
        }
        return "something went wrong";
    }
    
}
