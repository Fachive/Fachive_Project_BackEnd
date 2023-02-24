package com.facaieve.backend.security.oauth;

import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.repository.user.UserRepository;
import com.facaieve.backend.security.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
        UserEntity userEntity  = saveOrUpdate(attributes);// 유저가 없다면 저장하고 다른 유저가 존재한다면 업데이트 하도록 구현함.
        log.info("{}", userRequest);

        var memberAttribute = attributes.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().toString()))
                ,memberAttribute
                ,"email");//이게 왜 email

    }

    @Transactional
    public UserEntity saveOrUpdate(OAuthAttributes attributes){
        Optional<UserEntity> findUserEntity = userRepository.findByEmail(attributes.getEmail());

        if(findUserEntity.isPresent()){
            UserEntity userEntity = findUserEntity.get();
            getEmailConfirm(userEntity);
            userEntity.update(attributes.getName(), attributes.getPicture());
            return userEntity;
        }else{
            return userRepository.save(attributes.toEntity());
        }
    }


    private void getEmailConfirm(UserEntity userEntity) {
        userEntity.setEmailVerified(true);
    }
}
