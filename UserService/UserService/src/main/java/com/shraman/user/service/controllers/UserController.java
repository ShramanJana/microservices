package com.shraman.user.service.controllers;

import com.shraman.user.service.entities.User;
import com.shraman.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shraman.user.service.UserConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final User user) {
        User user1 = userService.saveUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable final String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable final String userId, @RequestBody final User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable final String userId) {
        return ResponseEntity.ok(userService.removeUser(userId));
    }
}
