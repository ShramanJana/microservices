package com.shraman.user.service.references.service;

import com.shraman.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.shraman.user.service.UserConstants.RATING_SERVICE_DISCOVERY_CLIENT;
import static com.shraman.user.service.UserConstants.RATING_SERVICE_URI;
import static com.shraman.user.service.UserConstants.USER_ID_KEY;

@FeignClient(name=RATING_SERVICE_DISCOVERY_CLIENT, path = RATING_SERVICE_URI)
public interface RatingServiceCaller {

    @GetMapping("/user/{" + USER_ID_KEY + "}")
    ResponseEntity<List<Rating>> fetchRatingsByUserId(@PathVariable final String userId);

    @PostMapping
    ResponseEntity<Rating> postNewRating(@RequestBody final Rating rating);
}
