package com.shraman.rating.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("user_ratings")
public class Rating {
    @Id
    private String ratingId;
    private String hotelId;
    private String userId;
    private Integer rating;
    private String feedback;
}
