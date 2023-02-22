package com.facaieve.backend.security;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Token {
    private String accessToken;
    private String refreshToken;
}
