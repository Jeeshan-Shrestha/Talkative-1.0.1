package com.chatapp.ChatAppV2.Services;

import java.util.List;
import java.util.stream.Collectors;

import com.chatapp.ChatAppV2.Jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Models.UserDTO;
import com.chatapp.ChatAppV2.Models.UserProfile;
import com.chatapp.ChatAppV2.Models.Users;
import com.chatapp.ChatAppV2.Repository.UserRepostory;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepostory userRepo;

    public Users registerUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String loginUser(Users user) throws Exception{
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtils.generateToken(user.getUsername());
        }
        throw new Exception("Bad Credentials");
    }

    public List<UserDTO> getAllUsers(){
        return userRepo.findAll()
            .stream()
            .map(user -> new UserDTO(user.getUsername()))
            .collect(Collectors.toList());
    }

    public UserProfile getUserProfile(String username)throws Exception{
        Users user = userRepo.findByUsername(username); 
        if (user == null){
            throw new UsernameNotFoundException("User doesnt exist");
        }
        return new UserProfile(user.getUsername(), 
                                user.getFollowers(), 
                                user.getFollowersCount(), 
                                user.getFollowing(), 
                                user.getFollowingCount(), 
                                user.getPosts());  
    }

}
