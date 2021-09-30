package com.example.user_management.controllers;

import com.example.user_management.models.User;
import com.example.user_management.models.UserValidationRequest;
import com.example.user_management.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping(value = "/register/user")
    public ResponseEntity<?> userRegistration(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getUsername() == null || user.getPassword() == null)
                return new ResponseEntity<>("Please fill all the mandatory fields(username,password,email)", HttpStatus.BAD_REQUEST);
            if (!userManagementService.isUserValid(user))
                return new ResponseEntity<>("username and email won't be allowed duplicate", HttpStatus.BAD_REQUEST);
            userManagementService.saveUser(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to registered the user : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/validate/user")
    public ResponseEntity<?> userValidation(@RequestBody UserValidationRequest userValidationRequest) {
        try {
            if (userValidationRequest != null && userValidationRequest.getEmail() != null
                    && userValidationRequest.getPassword() != null)
                return new ResponseEntity<>("Email and password should be need for user validation!", HttpStatus.BAD_REQUEST);
            if (userManagementService.authenticate(userValidationRequest))
                return new ResponseEntity<>("You are logged in to use our services", HttpStatus.OK);
            return new ResponseEntity<>("Invalid credentials!", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to validate the user : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get/users")
    public ResponseEntity<?> getUsers(){
        try{
            List<User> users = userManagementService.getUsers();
            if(users.isEmpty())
                return new ResponseEntity<>("Till now no user registered in our service!",HttpStatus.OK);
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to get users : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/user")
    public ResponseEntity<?> deleteUser(@RequestParam String email){
        try {
            if(userManagementService.deleteUser(email))
                return new ResponseEntity<>("User deleted successfully!",HttpStatus.OK);
            return new ResponseEntity<>("No one user have email "+email,HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete the user : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
