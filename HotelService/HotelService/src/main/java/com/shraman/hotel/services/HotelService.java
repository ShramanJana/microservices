package com.shraman.hotel.services;

import com.shraman.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    // Create hotel
    Hotel createHotel(Hotel hotel);

    // Get all hotels
    List<Hotel> getAllHotels();

    // Get by Id
    Hotel getHotelById(String id);

}
