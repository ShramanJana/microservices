package com.shraman.gateway.entity;

import lombok.*;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String userId;
    private String accessToken;
    private String tokenType;
    private long expiresAt;
    private Collection<String> authorities;
}
