package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.UserDTO;
import com.chatapp.ChatAppV2.Services.UserService;



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
    
}
