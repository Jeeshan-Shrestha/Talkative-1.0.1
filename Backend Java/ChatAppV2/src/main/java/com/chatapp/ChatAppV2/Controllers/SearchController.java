package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.BackendResponse;
import com.chatapp.ChatAppV2.Models.ProfileSearch;
import com.chatapp.ChatAppV2.Services.SearchService;


@RestController
public class SearchController {

    @Autowired
    private SearchService search;

    @GetMapping("/search")
    public ResponseEntity<BackendResponse> searchUser(@RequestParam String username) {
        List<ProfileSearch> searchUsers = search.searchUser(username);
        return new ResponseEntity<>(new BackendResponse(true, searchUsers),HttpStatus.OK);
    }
    
    
}
