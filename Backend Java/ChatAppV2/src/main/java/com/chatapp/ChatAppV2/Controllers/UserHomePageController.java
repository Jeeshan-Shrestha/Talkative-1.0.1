package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.UserDTO;
import com.chatapp.ChatAppV2.Models.UserProfile;
import com.chatapp.ChatAppV2.Services.UserService;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UserHomePageController {

    @Autowired
    UserService userService;

    @GetMapping("/home/users")
    public ResponseEntity<?> getAllUsers(){
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok().body(new BackendResponse(true, users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));
        }
    }

    @GetMapping("/home/user/profile/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        try{
            UserProfile userProfile = userService.getUserProfile(username);
            return ResponseEntity.ok().body(new BackendResponse(true, userProfile));
        }catch(UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, "Something went wrong"));
        }
    }
    
    
    
    
}
