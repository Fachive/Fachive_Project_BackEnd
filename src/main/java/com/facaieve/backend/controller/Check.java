package com.facaieve.backend.controller;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.security.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class Check {
    @Autowired
    TokenProvider tokenProvider;
    @GetMapping
    public ResponseEntity<?> home(@RequestParam String token){

        String userEmail = tokenProvider.validateAndGetUserEmail(token);
        String [] emailBuf = userEmail.split("@");

        return ResponseEntity.ok().body( UserDto.UserInfoAndToken.builder()
                .token(token)
                .displayName(emailBuf[0])
                .email(userEmail)
                .build());
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@AuthenticationPrincipal String userId){
        return ResponseEntity.ok().body(userId);
    }
}
