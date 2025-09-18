package com.chatapp.ChatAppV2.Models;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Document(collection = "ChatAppUsers")
@Data
@Builder
@AllArgsConstructor
public class Users {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String username;

    private String password;

    @Indexed(unique=true)
    private String gmail;

    private Map<String,List<ChatMessage>> chats;

    private Set<String> followers;

    private Integer followersCount;

    private Set<String> following;

    private Integer followingCount;

    private List<Post> posts;

    private LocalDate date;

}
