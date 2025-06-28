package com.chatapp.ChatAppV2.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users entity) {

        String auth = userService.loginUser(entity);
        if (auth.equalsIgnoreCase("bad")) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"bad credentials"));
        }

        return ResponseEntity.ok().body(new BackendResponse(true, auth));
    }

}
