package com.facaieve.backend.security.oauth;

import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.security.oauth.dto.OAuthAttributes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Getter
@Slf4j
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OidcUserRequest,OidcUser> delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oidcUser.getAttributes());
        UserEntity userEntity  = saveOrUpdate(attributes);// 유저가 없다면 저장하고 다른 유저가 존재한다면 업데이트 하도록 구현함.
        log.info("{}", userRequest);

        var memberAttribute = attributes.convertToMap();

        return oidcUser;
    }



    private UserEntity saveOrUpdate(OAuthAttributes attributes){
        Optional<UserEntity> findUserEntity = userRepository.findByEmail(attributes.getEmail());

        if(findUserEntity.isPresent()){
            UserEntity userEntity = findUserEntity.get();
            userEntity.update(attributes.getName(), attributes.getPicture());
            return userEntity;
        }else{
            return userRepository.save(attributes.toEntity());
        }
    }
}
