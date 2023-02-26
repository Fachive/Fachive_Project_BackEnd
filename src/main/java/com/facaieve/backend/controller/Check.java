package com.facaieve.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("home")
public class Check {
    @GetMapping
    public ResponseEntity<?> home(@RequestParam String token){
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@AuthenticationPrincipal String userId){
        return ResponseEntity.ok().body(userId);
    }
}
