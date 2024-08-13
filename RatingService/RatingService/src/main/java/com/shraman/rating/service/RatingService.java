package com.shraman.rating.service;

import com.shraman.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    // Create hotel
    Rating createRating(Rating rating);

    // Get all hotels
    List<Rating> getAllRatings();

    // Get by UserId
    List<Rating> getRatingByUserId(String userId);

    // Get by HotelId
    List<Rating> getRatingByHotelId(String hotelId);
}
