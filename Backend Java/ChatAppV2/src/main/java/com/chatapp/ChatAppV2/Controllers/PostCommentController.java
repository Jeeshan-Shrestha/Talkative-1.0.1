package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.Comment;
import com.chatapp.ChatAppV2.Models.CommentDTO;
import com.chatapp.ChatAppV2.Services.PostCommentService;



@RestController
public class PostCommentController {
    
    @Autowired
    PostCommentService postCommentService;

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody Comment comment) {
       try{
        String postComment = postCommentService.postComment(comment);
        return ResponseEntity.ok().body(new BackendResponse(true,postComment));
       }catch(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BackendResponse(false, e.getMessage()));
       }catch(RuntimeException e){
        return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
       }catch (Exception e){
        return ResponseEntity.badRequest().body(new BackendResponse(false, "Something went wrong"));
       }
    }

    @GetMapping("/comment")
    public List<CommentDTO> getAllCommentFromPost(@RequestParam String postId) {
        return postCommentService.getAllCommentsFromPost(postId);
    }
    
    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId){
        String message = postCommentService.deleteComment(commentId);
        return ResponseEntity.ok().body(new BackendResponse(true,message));
    }

    @GetMapping("/comment/like")
    public ResponseEntity<?> likeComment(@RequestParam String commentId) {
        String message = postCommentService.likeComment(commentId);
        return ResponseEntity.ok().body(new BackendResponse(true,message));
    }
    

}
