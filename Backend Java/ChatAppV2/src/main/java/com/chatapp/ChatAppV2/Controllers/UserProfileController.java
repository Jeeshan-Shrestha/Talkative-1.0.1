package com.chatapp.ChatAppV2.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.ChatAppV2.Exceptions.AlreadyFollowedException;
import com.chatapp.ChatAppV2.Exceptions.SelfFollowException;
import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.UserProfile;
import com.chatapp.ChatAppV2.Services.UserProfileService;



@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/followers/{username}")
    public ResponseEntity<?> getAllFollowers(@PathVariable String username){
        try{
            Set<String> allFollowers = userProfileService.getAllFollowers(username);
            return ResponseEntity.ok().body(new BackendResponse(true,allFollowers));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));

        }
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<?> getAllFollowing(@PathVariable String username){
        try{
            Set<String> allFollowing = userProfileService.getAllFollowing(username);
            return ResponseEntity.ok().body(new BackendResponse(true,allFollowing));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));
        }
    }

    @GetMapping("/follow")
    public ResponseEntity<?> followSomeone(@RequestParam String followedTo){
        try{
            String followedMsg = userProfileService.follow(followedTo);
            return ResponseEntity.ok().body(new BackendResponse(true,followedMsg));
        } catch(AlreadyFollowedException | SelfFollowException e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        } 
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false,"something went wrong"));
        }
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        try{
            UserProfile userProfile = userProfileService.getUserProfile(username);
            return ResponseEntity.ok().body(new BackendResponse(true, userProfile));
        }catch(UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, "Something went wrong"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        try{
            UserProfile userProfile = userProfileService.getSelfProfile();
            return ResponseEntity.ok().body(new BackendResponse(true, userProfile));
        }catch(UsernameNotFoundException e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, e.getMessage()));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(new BackendResponse(false, "Something went wrong"));
        }
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<?> editProfile(
        @RequestPart("avatar") MultipartFile avatar,
        @RequestPart("bio") String bio,
        @RequestPart("displayName") String displayName,
        @RequestPart("coverPhoto") MultipartFile coverPhoto
    ) {
        try {
            String change = userProfileService.editProfile(avatar,bio,displayName,coverPhoto);
            return ResponseEntity.ok().body(new BackendResponse(true, change));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BackendResponse(false, "Something went wrong"));
        }
    }
    

}
