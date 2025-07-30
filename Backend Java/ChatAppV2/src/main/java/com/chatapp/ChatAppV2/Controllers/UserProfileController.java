package com.chatapp.ChatAppV2.Controllers;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/followers/{username}")
    public ResponseEntity<?> getAllFollowers(@PathVariable String username){
        try{
            List<String> allFollowers = userProfileService.getAllFollowers(username);
            return ResponseEntity.ok().body(new BackendResponse(true,allFollowers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));

        }
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<?> getAllFollowing(@PathVariable String username){
        try{
            List<String> allFollowing = userProfileService.getAllFollowing(username);
            return ResponseEntity.ok().body(new BackendResponse(true,allFollowing));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));
        }
    }

    @GetMapping("/follow/{followedBy}/{followedTo}")
    public ResponseEntity<?> followSomeone(@PathVariable String followedBy, @PathVariable String followedTo){
        try{
            String followedMsg = userProfileService.follow(followedBy, followedTo);
            return ResponseEntity.ok().body(new BackendResponse(true,followedMsg));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));
        }
    }

}
