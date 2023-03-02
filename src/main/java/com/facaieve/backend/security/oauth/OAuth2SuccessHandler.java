package com.facaieve.backend.security.oauth;

import com.facaieve.backend.mapper.JwtRequestMapper;
import com.facaieve.backend.security.TokenProvider;
import com.facaieve.backend.security.jwt.JwtRequest;
import com.facaieve.backend.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final JwtRequestMapper jwtRequestMapper;
    private final  ObjectMapper objectMapper;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        log.info("email: {}", oAuth2User.getName());

        JwtRequest jwtRequest = jwtRequestMapper.auth2UserToJwtRequest(oAuth2User);

        String targetUrl;
        log.info("토큰 발행 시작");

        String token = tokenProvider.create(jwtRequest);
        log.info("{}", token);

        targetUrl = UriComponentsBuilder.fromUriString("/")//redirect url 만드는 부분
                .queryParam("token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
