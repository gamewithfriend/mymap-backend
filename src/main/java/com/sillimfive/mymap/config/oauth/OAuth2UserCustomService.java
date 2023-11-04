package com.sillimfive.mymap.config.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sillimfive.mymap.config.oauth.dto.OAuthAttributes;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException {
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);

        //구글, 카카오 구분을 위한 로직이 필요하다
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, user.getAttributes());

        saveOrUpdate(attributes);
        return user; // oauth id 값 모두 들어간 객체를 반환 시킨다
    }

    private User saveOrUpdate(OAuthAttributes attributes) {

        String email = attributes.getEmail();
        String name = attributes.getName();
        log.info("email = {}", email);
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.changeNickName(name))
                .orElse(User.builder()
                        .email(email)
                        .nickName(name)
                        .build());

        return userRepository.save(user);

    }
}
