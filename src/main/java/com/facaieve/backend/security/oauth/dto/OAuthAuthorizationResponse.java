package com.facaieve.backend.security.oauth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class OAuthAuthorizationResponse {
    private String accessToken;

    @Builder
    public OAuthAuthorizationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}