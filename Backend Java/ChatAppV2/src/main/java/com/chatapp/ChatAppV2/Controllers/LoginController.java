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
            Users user = userService.registerUser(entity);
            // if (user == null) {
            // return ResponseEntity.badRequest()
            // .body("A person with that username already exists in the database try logging
            // in ");
            // }
            return ResponseEntity.ok().body(new BackendResponse(true, "User Registered"));
        } catch (Exception e) {
            System.out.println("some error occured");
            return ResponseEntity.internalServerError().body(new BackendResponse(false, "Something went wrong"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users entity) {

        String auth = userService.loginUser(entity);
        if (auth.equalsIgnoreCase("bad")) {
            return ResponseEntity.badRequest().body("bad Credentials");
        }

        return ResponseEntity.ok().body(new BackendResponse(true, auth));
    }

}
