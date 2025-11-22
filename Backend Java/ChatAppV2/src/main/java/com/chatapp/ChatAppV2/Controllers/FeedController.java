package com.chatapp.ChatAppV2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.ChatAppV2.Models.PostDTO;
import com.chatapp.ChatAppV2.Services.FeedService;



@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;
    
    @GetMapping("/home")
    public List<PostDTO> getHomeFeed() {
        List<PostDTO> homeFeed = feedService.getHomeFeed();
        return homeFeed;
    }
    

}
