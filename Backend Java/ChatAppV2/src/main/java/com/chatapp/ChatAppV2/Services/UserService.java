package com.chatapp.ChatAppV2.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatapp.ChatAppV2.Jwt.JwtUtils;
import com.chatapp.ChatAppV2.Models.LoginDetails;
import com.chatapp.ChatAppV2.Models.UserDTO;
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

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    public Users registerUser(Users user) {
        user.setDisplayName(user.getUsername());
        user.setJoinDate(LocalDate.now());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String loginUser(LoginDetails user) throws Exception{
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsernameOrGmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            MyUserDetails userDetails = (MyUserDetails) myUserDetailsService.loadUserByUsername(user.getUsernameOrGmail());
            return jwtUtils.generateToken(userDetails);
        }
        throw new Exception("Bad Credentials");
    }

    public List<UserDTO> getAllUsers(){
        return userRepo.findAll()
            .stream()
            .map(user -> new UserDTO(user.getUsername()))
            .collect(Collectors.toList());
    }

    public List<Users> getUsers(){
        return userRepo.findAll();
    }

    
}
