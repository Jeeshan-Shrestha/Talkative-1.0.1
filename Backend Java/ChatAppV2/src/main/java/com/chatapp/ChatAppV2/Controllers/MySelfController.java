package com.chatapp.ChatAppV2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.MySelf;
import com.chatapp.ChatAppV2.Services.MySelfService;


@RestController
@RequestMapping("/myself")
public class MySelfController {

    @Autowired
    private MySelfService mySelfService;
    
    @GetMapping
    public ResponseEntity<BackendResponse> getMySelf() {
        MySelf myself = mySelfService.getMySelf();
        return ResponseEntity.ok().body(new BackendResponse(true,myself));
    }
    

}
