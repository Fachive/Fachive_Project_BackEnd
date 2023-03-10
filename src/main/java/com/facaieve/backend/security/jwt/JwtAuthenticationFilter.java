package com.facaieve.backend.security.jwt;
//import com.facaieve.backend.security.JwtUtil;
import com.facaieve.backend.security.TokenProvider;
import com.facaieve.backend.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {//OncePerRequestFilter 를 사용해서 요청당 한번만 수행될 수 있도록 만듦

    public JwtAuthenticationFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (!Objects.isNull(authorization)){
            try {

                // 요청에서 토큰 가져오기.
                String token = parseBearerToken(request);
//            log.info("Filter is running...");
                // 토큰 검사하기. JWT이므로 인가 서버에 요청 하지 않고도 검증 가능.
                if (token != null && !token.equalsIgnoreCase("null")) {
                    // userId 가져오기. 위조 된 경우 예외 처리 된다.
                    String userEmail = tokenProvider.validateAndGetUserEmail(token);//우리의 경우에 사용자의 이메일을 기준으로 만듦 넘어온 토큰을 검증함
                    log.info("Authenticated user Email : " + userEmail);
                    // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
//                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//
//                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities()); // todo 추후에 변경할것
                    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userEmail, // 인증된 사용자의 정보. 문자열이 아니어도 아무거나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는데, 우리는 안 만들었음.
                            null, //
                            AuthorityUtils.NO_AUTHORITIES
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);//context 에 등록해서 local thread 에 저장
                }
            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }
    }
        filterChain.doFilter(request, response);
    }
    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }



}

