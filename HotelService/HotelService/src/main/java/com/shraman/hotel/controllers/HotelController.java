package com.shraman.hotel.controllers;


import com.shraman.hotel.entities.Hotel;
import com.shraman.hotel.services.HotelService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shraman.hotel.ServiceConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody final Hotel hotelData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotelData));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable final String hotelId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(hotelId));
    }
}
