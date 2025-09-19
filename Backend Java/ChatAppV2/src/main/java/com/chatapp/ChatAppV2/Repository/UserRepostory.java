package com.chatapp.ChatAppV2.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.ChatAppV2.Models.Users;

public interface UserRepostory extends MongoRepository<Users, ObjectId> {

    Users findByUsername(String username);

    Users findByGmail(String gmail);

    List<Users> findByUsernameStartingWithIgnoreCase(String prefix);

}
