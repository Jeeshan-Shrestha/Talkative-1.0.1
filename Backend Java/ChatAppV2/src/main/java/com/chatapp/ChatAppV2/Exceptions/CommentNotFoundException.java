package com.chatapp.ChatAppV2.Exceptions;

public class CommentNotFoundException extends RuntimeException{
    
     public CommentNotFoundException(String message){
        super(message);
    }

}
