package com.example.user_management.services;

import com.example.user_management.models.User;
import com.example.user_management.models.UserValidationRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementService {

    //Using list instead of using database
    private static final List<User> users = new ArrayList<>();

    public void saveUser(User user) {
        users.add(user);
    }

    public boolean authenticate(UserValidationRequest userValidationRequest) {
        return users.stream().anyMatch(u -> u.getEmail().equals(userValidationRequest.getEmail())
                && u.getPassword().equals(userValidationRequest.getPassword()));
    }

    public List<User> getUsers(){
        return users;
    }

    public boolean deleteUser(String email){
        return users.removeIf(u->u.getEmail().equals(email));
    }

    public boolean isUserValid(User user) {
        if (users.isEmpty())
            return true;
        return users.stream().noneMatch(u -> u.getUsername().equals(user.getUsername())
                || u.getEmail().equals(user.getEmail()));
    }
}
