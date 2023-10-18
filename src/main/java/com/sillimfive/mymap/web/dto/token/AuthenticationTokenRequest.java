package com.sillimfive.mymap.web.dto.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationTokenRequest {

    private String accessToken;
    private String tokenType;

    @Builder
    public AuthenticationTokenRequest(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
