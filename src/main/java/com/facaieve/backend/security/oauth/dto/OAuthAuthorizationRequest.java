package com.facaieve.backend.security.oauth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OAuthAuthorizationRequest {

    private String username;
    private String password;

}