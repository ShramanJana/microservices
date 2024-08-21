package com.shraman.user.service.controllers;

import com.shraman.user.service.entities.User;
import com.shraman.user.service.services.UserService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.shraman.user.service.UserConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String FALLBACK_MESSAGE = "Fallback executed because some of the services are down, {}";
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final User user) {
        User user1 = userService.saveUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "getUserByIdFallback")
    @Retry(name = "ratingHotelRetryService", fallbackMethod = "getUserByIdFallback")
    @RateLimiter(name = "ratingHotelLimiter", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<User> getUserById(@PathVariable final String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    public ResponseEntity<User> getUserByIdFallback(final String userId, final Exception ex) {
        log.error(FALLBACK_MESSAGE, ex.getMessage());
        User user = User.builder()
                .userId(userId)
                .email("Dummy@email.com")
                .name("Dummy User").build();
        return ResponseEntity.ok().body(user);
    }
    @GetMapping
//    @CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "getAllUsersFallback")
//    @Retry(name = "ratingHotelRetryService", fallbackMethod = "getAllUsersFallback")
    @RateLimiter(name = "ratingHotelLimiter", fallbackMethod = "getAllUsersFallback2")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Calling getAllUsers");
        return ResponseEntity.ok(userService.getAllUser());
    }

    public ResponseEntity<List<User>> getAllUsersFallback(final FeignException ex) {
        log.error(FALLBACK_MESSAGE, ex.toString());
        List<User> userList= new ArrayList<>();
        User user = User.builder()
                .userId("123456789")
                .email("Dummy@email.com")
                .name("Dummy User").build();
        userList.add(user);
        return ResponseEntity.ok().body(userList);
    }
    public ResponseEntity<List<User>> getAllUsersFallback2(final Exception ex) {
        log.error("Fallback executed because of too many requests, {}", ex.toString());
        List<User> userList= new ArrayList<>();
        User user = User.builder()
                .userId("123456789")
                .email("Dummy@email.com")
                .name("Dummy User").build();
        userList.add(user);
        return ResponseEntity.ok().body(userList);
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
