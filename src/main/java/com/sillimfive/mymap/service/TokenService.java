package com.sillimfive.mymap.service;

import com.sillimfive.mymap.config.jwt.TokenProvider;
import com.sillimfive.mymap.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUser().getId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));

    }

}
