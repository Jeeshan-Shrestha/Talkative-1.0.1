package com.chatapp.ChatAppV2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.LoginDetails;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users entity) {
        try {
            userService.registerUser(entity);
            return ResponseEntity.ok().body(new BackendResponse(true, "User Registered"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false, "Username Already exists"));
        }

    }

   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDetails entity) throws Exception {
        String token = userService.loginUser(entity);
        return ResponseEntity.ok(new BackendResponse(true, token));
    } 
}
