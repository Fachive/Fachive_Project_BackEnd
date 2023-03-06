package com.facaieve.backend.config.security;

import com.facaieve.backend.security.oauth.CustomOidcUserService;
import com.facaieve.backend.security.oauth.OAuth2SuccessHandler;
import com.facaieve.backend.security.TokenProvider;
import com.facaieve.backend.security.oauth.CustomOAuth2UserService;
import com.facaieve.backend.security.CustomUserDetailsService;
import com.facaieve.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final CustomOAuth2UserService customOAuth2Service;
    @Autowired
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Autowired
    private final TokenProvider tokenProvider;

    @Autowired
    private final CustomOidcUserService oidcUserService;

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, /favicon.ico, /**/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // swagger

        web.ignoring().antMatchers(
                "/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**", "/swagger/**",
                "/favicon.ico", "/**/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), LogoutFilter.class);


        // http 시큐리티 빌더
        http
                .cors()
                .and()
                .csrf()// csrf는 현재 사용하지 않으므로 disable
                .disable()
                .httpBasic()// token을 사용하므로 basic 인증 disable
                .disable()
                .sessionManagement()  // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// restful 하게 구현하기 위해서 설정함
            .and()
                .authorizeRequests()// **/auth/** 경로는 인증 안해도 됨.
                .antMatchers("/**/auth/**", "/**/email/**",
                        "/", "/home/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/swagger/**",
                        "/webjars/**",
                        "/v3/api-docs"
                        , "/user/auth/**"
                        , "/email/confirm-email"
                        ,"/login/oauth2/code/"
                ,"/fashionpickup/**", "/user/delete").permitAll()// main page 는 열어둠 todo 마지막에 배포할 때는 제외하고 배포할 것
                .antMatchers("/oauth/**", "/login").permitAll()//소셜 로그인을 위한 경로 허용함
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/authorize", "/authorize/refresh", "/users").anonymous()
                .antMatchers(HttpMethod.POST, "/oauth/unlink").authenticated()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest()// **/auth/**이외의 모든 경로는 인증 해야됨.
                .authenticated();// 이 외의 모등 요청은 인증을


        http.oauth2Login()
                .userInfoEndpoint().userService(customOAuth2Service)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(new AuthenticationEntryPointFailureHandler(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

                    }
                }));



        http.logout()
                .deleteCookies("JSESSIONID");

        http.cors().configurationSource(corsConfigurationSource());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set the user details service and authentication provider
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }//자체 적으로 구현한 userDetail service 와 pasowordEncoder 를 사용해서 구현하겠다는 의미이다

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // Set up a persistent token repository for remember me functionality
        // This example uses an in-memory implementation, but you may want to use a database-backed implementation for production use
        InMemoryTokenRepositoryImpl tokenRepository = new InMemoryTokenRepositoryImpl();
        return tokenRepository;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add("Authorization");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedOriginPattern("http://localhost:8080");
        configuration.addAllowedOriginPattern("http://localhost:3000");
        configuration.addAllowedOriginPattern("https://fachive.netlify.app");
        configuration.addAllowedOriginPattern("https://fachive.kro.kr/");
//        configuration.addAllowedOriginPattern("http://ec2-54-180-7-198.ap-northeast-2.compute.amazonaws.com:8080/");
//        configuration.setAllowCredentials(true); // 다음 에러로 변경 addAllowedOriginPattern로 대체 When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header.


        configuration.addAllowedHeader("*");//모든 종류의 헤더값 공유 허용
        configuration.addAllowedMethod("*");//교차공유시 모든 http요청 허용
        configuration.setExposedHeaders(allowedHeaders);


        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

