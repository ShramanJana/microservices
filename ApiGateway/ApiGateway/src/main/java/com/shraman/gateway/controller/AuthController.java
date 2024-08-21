package com.shraman.gateway.controller;

import com.shraman.gateway.entity.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
                                              @AuthenticationPrincipal OidcUser user, Model model) {
        LOGGER.info("User Email Id: {}", user.getEmail());

        // setting auth response values
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(user.getEmail());
        authResponse.setAccessToken(client.getAccessToken().getTokenValue());
        authResponse.setTokenType(client.getAccessToken().getTokenType().getValue());
        authResponse.setExpiresAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        List<String> authorityList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        authResponse.setAuthorities(authorityList);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
