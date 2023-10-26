package com.sillimfive.mymap.web.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationTokenRequest {

    @Schema(example = "aVCUWj0XoBPHBv10z4M16UoRVG7ORiHRwKKwzTAABi1XRZa8t")
    private String accessToken;
    @Schema(example = "kakao")
    private String tokenType;

    @Builder
    public AuthenticationTokenRequest(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
