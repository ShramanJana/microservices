package com.shraman.user.service.entities;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
     private String ratingId;
     private String userId;
     private String hotelId;
     private Integer rating;
     private String feedback;
     private Hotel hotel;

}

