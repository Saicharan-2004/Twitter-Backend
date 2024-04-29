package com.example.TwitterProject.User;

import com.example.TwitterProject.Comment.CommentResponse;
import com.example.TwitterProject.ErrorResponse;
import com.example.TwitterProject.Exception.UserAlreadyExistsException;
import com.example.TwitterProject.Exception.UserNotFoundException;
import com.example.TwitterProject.Post.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        UserAccount existingUser = userRepository.findByEmail(loginRequest.getEmail());
        if (existingUser != null) {
            if (existingUser.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login Successful");
            } else {
                return new ResponseEntity<>(new ErrorResponse("Username/Password Incorrect"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse("User does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody UserAccount user) {
        try {
            UserAccount existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                throw new UserAlreadyExistsException("Forbidden, Account already exists");
            }
            userRepository.save(user);
            return new ResponseEntity<>("Account Creation Successful", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseMini>> getAllUsers() {
        List<UserResponseMini> users = userRepository.findAll().stream().map(user -> {
            UserResponseMini userResponse = new UserResponseMini();
            userResponse.setName(user.getName());
            userResponse.setUserID(user.getId());
            userResponse.setEmail(user.getEmail());
            return userResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam int userID) {
        try {
            UserAccount user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User does not exist"));
            UserResponseMini users = new UserResponseMini();
            users.setName(user.getName());
            users.setUserID(user.getId());
            users.setEmail(user.getEmail());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}

