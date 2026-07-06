package com.tapp.tserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tapp.tserver.model.User;
import com.tapp.tserver.repository.UserRepo;

@Service
public class UserService {
    
    private final UserRepo repo;
    public UserService(UserRepo repo){
        this.repo = repo;
    }

    public void syncUser(User userFromToken) {
       repo.upsertUser(userFromToken); 
    }

    public String getUserRoleByEmail(String email) {
       return repo.findUserRoleByEmail(email).orElseThrow(()-> new IllegalArgumentException("User not Found")); 
    }

    public User getUserByEmail(String email) {
       return repo.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not Found")); 
    }

    public List<User> getAllUsers() {
       return repo.findAllUsers(); 
    }
}
