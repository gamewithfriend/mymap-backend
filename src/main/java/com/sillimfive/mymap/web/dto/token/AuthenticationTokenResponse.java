package com.sillimfive.mymap.web.dto.token;

import com.sillimfive.mymap.web.dto.user.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationTokenResponse {

    private String accessToken;
    private String refreshToken;
    private UserResponseDto user;
    private Long accessTokenExpiredTime;
    private Long refreshTokenExpiredTime;

    @Builder
    public AuthenticationTokenResponse(String accessToken, String refreshToken, UserResponseDto user, Long accessTokenExpiredTime, Long refreshTokenExpiredTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }
}
