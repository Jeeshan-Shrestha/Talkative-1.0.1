package com.chatapp.ChatAppV2.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chatapp.ChatAppV2.Jwt.JwtFilter;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    UserRepostory userRepo;

    public Users registerUser(Users user) {
        Users userOnDatabase = userRepo.findByUsername(user.getUsername());
        // if (userOnDatabase != null) {
        // System.out.println("Same user");
        // return null;
        // }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String loginUser(Users user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtFilter.generateKey();
        }
        return "bad";
    }

}
