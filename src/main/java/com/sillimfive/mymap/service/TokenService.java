package com.sillimfive.mymap.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sillimfive.mymap.config.auth.jwt.TokenProvider;
import com.sillimfive.mymap.domain.RefreshToken;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RefreshTokenRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.web.dto.token.AuthenticationTokenResponse;
import com.sillimfive.mymap.web.dto.token.payload.GooglePayload;
import com.sillimfive.mymap.web.dto.token.payload.KakaoPayload;
import com.sillimfive.mymap.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final RefreshTokenRepository refreshTokenRepository;

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(1);

    private final static String TOKEN_TYPE_KAKAO = "kakao";
    private final static String TOKEN_TYPE_GOOGLE = "google";
    private final static String USER_INFO_KAKAO = "https://kapi.kakao.com/v2/user/me";
    private final static String USER_INFO_GOOGLE = "https://www.googleapis.com/userinfo/v2/me";


    public String createNewAccessToken(String refreshToken){
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUser().getId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

    }

    public AuthenticationTokenResponse getAuthTokenResponse(String accessToken, String tokenType){

        User oAuthUser = getOauthUser(accessToken, tokenType);
        // todo - 저장 서비스, 토큰 발급 서비스  -> 이후 return spec 파싱
        long count = userRepository.count() +1L;
        String settingNickName ="MYMAP" +count;
        User insertUser = User.builder()
                .nickName(settingNickName)
                .email(oAuthUser.getEmail())
                .loginId(oAuthUser.getLoginId())
                .oAuthType(oAuthUser.getOAuthType())
                .build();

        User user = userRepository.findByEmail(oAuthUser.getEmail())
                    .orElseGet(() ->userRepository.save(insertUser));

        AuthenticationTokenResponse authTokenResponse = createAuthTokenResponse(user);
        saveRefreshToken(user.getId(), authTokenResponse.getRefreshToken());

        Authentication authentication = tokenProvider.getAuthentication(authTokenResponse.getAccessToken());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return authTokenResponse;
    }

    public AuthenticationTokenResponse createAuthTokenResponse (User user) {

        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

        UserResponseDto userResponseDto = UserResponseDto
                .builder()
                .user(user)
                .build();

        return AuthenticationTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userResponseDto)
                .accessTokenExpiredTime(ACCESS_TOKEN_DURATION.toMillis())
                .refreshTokenExpiredTime(REFRESH_TOKEN_DURATION.toMillis())
                .build();
    }

    // 카카오 : https://kapi.kakao.com/v2/user/me
    /**
     * curl -v -X GET "https://kapi.kakao.com/v2/user/me" \
     *   -H "Authorization: Bearer ${ACCESS_TOKEN}"
     * */

    // 구글 : https://www.googleapis.com/userinfo/v2/me

    /**
     * GET - https://www.googleapis.com/userinfo/v2/me
     * 헤더 "Authorization: Bearer ${ACCESS_TOKEN}"
     * */
    public User getOauthUser(String accessToken, String tokenType){
        log.info("accessToken : {}", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity requestHeader = new HttpEntity(headers);

        // uri 세팅 메서드 RestTemplate 사용 예정
        ResponseEntity<String> responseEntity = restTemplate.exchange(setApiUri(tokenType), HttpMethod.GET, requestHeader, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()){

            // todo payload 값 확인
            return getPayload(tokenType, responseEntity.getBody());
        } else
            throw new ResponseStatusException(responseEntity.getStatusCode());
    }

    private String setApiUri(String tokenType){

        if(TOKEN_TYPE_KAKAO.equals(tokenType)){
            return USER_INFO_KAKAO;
        }
        return USER_INFO_GOOGLE;
    }

    private User getPayload(String tokenType, String payloadBody){

        if(TOKEN_TYPE_KAKAO.equals(tokenType)){
            return kakaoPayload(payloadBody);
        }
        return googlePayload(payloadBody);
    }

    private User googlePayload(String payloadBody){
        //구글 userinfo 값 꺼내 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GooglePayload payload = objectMapper.readValue(payloadBody, GooglePayload.class);
            // todo user 객체 데이터 확인 필요 -
            log.debug("google payload : {}", payload);

            // googleinfo 객체로 받은후 user에 세팅
            return User.builder()
                    .loginId(payload.getSub())
                    .nickName(UUID.randomUUID().toString().split("-")[0])
                    .email(payload.getEmail())
                    .build();
        }catch (Exception e){
            //todo 예외 처리
            throw new IllegalArgumentException(e);
        }
    }

    private User kakaoPayload(String payloadBody){
        //카카오 userinfo 값 꺼내 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            KakaoPayload payload = objectMapper.readValue(payloadBody, KakaoPayload.class);
            // todo user 객체 데이터 확인 필요
            log.debug("kakao payload : {}", payload);

            return User.builder()
                    .loginId(payload.getId())
                    .nickName(UUID.randomUUID().toString().split("-")[0])
                    .email(payload.getKakao_account().getEmail())
                    .build();
        }catch (Exception e){
            //todo 예외 처리
            throw new IllegalArgumentException(e);
        }
    }

    public void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userService.findById(userId), newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

}
