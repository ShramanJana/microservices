package com.shraman.user.service.references.service;

import com.shraman.user.service.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.shraman.user.service.UserConstants.HOTEL_SERVICE_DISCOVERY_CLIENT;
import static com.shraman.user.service.UserConstants.HOTEL_SERVICE_URI;
import static com.shraman.user.service.UserConstants.HOTEL_ID_KEY;

@FeignClient(name = HOTEL_SERVICE_DISCOVERY_CLIENT)
public interface HotelServiceCaller {

    @GetMapping(HOTEL_SERVICE_URI +"/{" + HOTEL_ID_KEY +"}")
    Hotel getHotel(@PathVariable final String hotelId);
}
