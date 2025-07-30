package com.chatapp.ChatAppV2.Controllers;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.Post;
import com.chatapp.ChatAppV2.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getPostsFromUser(@PathVariable String username){
        try{
            List<Post> postsFromUser = postService.getPostsFromUser(username);
            return ResponseEntity.ok().body(new BackendResponse(true,postsFromUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BackendResponse(false,"something went wrong"));
        }
    }

}
