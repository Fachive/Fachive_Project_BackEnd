package com.facaieve.backend.mapper;

import com.facaieve.backend.Constant.UserRole;
import com.facaieve.backend.security.jwt.JwtRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class JwtRequestMapper {
    public JwtRequest auth2UserToJwtRequest(OAuth2User oAuth2User){

        var attributes = oAuth2User.getAttributes();

        return JwtRequest.builder()
                .email((String)attributes.get("email"))
                .role(UserRole.GENERAL)// todo 어케처리할지 모르겠어서 일단 넣고 본다 시...
                .build();
    }
}
