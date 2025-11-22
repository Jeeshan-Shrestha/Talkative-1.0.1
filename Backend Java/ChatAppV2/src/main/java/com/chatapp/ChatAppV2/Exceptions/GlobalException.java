package com.chatapp.ChatAppV2.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chatapp.ChatAppV2.Models.BackendResponse;

@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(SelfFollowException.class)
    public ResponseEntity<BackendResponse> selfFollowException(){
        return ResponseEntity.badRequest().body(new BackendResponse(false, "You cant follow yourself :3"));
    }

    //* this will probably never be used */
    @ExceptionHandler(AlreadyFollowedException.class)
    public ResponseEntity<BackendResponse> alreadyFollowedException(){
        return ResponseEntity.badRequest().body(new BackendResponse(false, "You have already followed that user"));
    }

    //* this will probably never be used */
    @ExceptionHandler(AlreadyUnfollowedException.class)
    public ResponseEntity<BackendResponse> alreadyUnfollowedException(){
        return ResponseEntity.badRequest().body(new BackendResponse(false, "you have already unfollowed that user"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BackendResponse> usernameNotFoundException(){
        return ResponseEntity.badRequest().body(new BackendResponse(false, "No user of that name exists"));
    }

}
