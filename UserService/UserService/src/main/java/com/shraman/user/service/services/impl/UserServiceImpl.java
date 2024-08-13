package com.shraman.user.service.services.impl;

import com.shraman.user.service.entities.Hotel;
import com.shraman.user.service.entities.Rating;
import com.shraman.user.service.entities.User;
import com.shraman.user.service.exceptions.ResourceNotFoundException;
import com.shraman.user.service.references.service.HotelServiceCaller;
import com.shraman.user.service.references.service.RatingServiceCaller;
import com.shraman.user.service.repositories.UserRepository;
import com.shraman.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelServiceCaller hotelServiceCaller;

    @Autowired
    private RatingServiceCaller ratingServiceCaller;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> usersList = userRepository.findAll();
        if(!usersList.isEmpty()) {
            usersList.forEach(this::populateRatingsDataToUserObject);
        }
        return usersList;
    }

    @Override
    public User getUser(String userId) {
        User userObject =  userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id not found on server : " + userId));

        populateRatingsDataToUserObject(userObject);

        return userObject;

    }

    private void populateRatingsDataToUserObject(User userObject) {
//        Rating[] ratingsArray = restTemplate.getForObject("http://RATING-SERVICE/api/v1/ratings/user/" + userObject.getUserId(), Rating[].class);
//        if(ratingsArray!=null && ratingsArray.length !=0) {
        ResponseEntity<List<Rating>> ratingsResponse = ratingServiceCaller.fetchRatingsByUserId(userObject.getUserId());
        if(ratingsResponse.getStatusCode().is2xxSuccessful()) {
            List<Rating> ratings = ratingsResponse.getBody();
            if (ratings != null) {
                ratings.forEach(rating -> {
//                    Hotel hotelObject = restTemplate.getForObject("http://HOTEL-SERVICE/api/v1/hotel/" + rating.getHotelId(), Hotel.class);

                    Hotel hotelObject = hotelServiceCaller.getHotel(rating.getHotelId());
                    rating.setHotel(hotelObject);
                });
            }

            userObject.setRatings(ratings);
        }
    }

    @Override
    public User updateUser(String userId, User userData) {
        User existingUserData = getUser(userId);
        String name = userData.getName().isBlank() ? existingUserData.getName() : userData.getName();
        String email = userData.getEmail().isBlank() ? existingUserData.getEmail() : userData.getEmail();
        String about = userData.getAbout().isBlank() ? existingUserData.getAbout() : userData.getAbout();
        existingUserData.setName(name);
        existingUserData.setEmail(email);
        existingUserData.setAbout(about);
        return userRepository.save(existingUserData);
    }

    @Override
    public User removeUser(String userId) {
        User existingUserData = getUser(userId);
        userRepository.deleteById(userId);
        return existingUserData;
    }
}
