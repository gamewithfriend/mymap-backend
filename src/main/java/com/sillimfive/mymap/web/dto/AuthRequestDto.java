package com.sillimfive.mymap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequestDto {
    @NotBlank
    @Schema(description = "OAuth 인가서버에서 받은 accessToken",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9")
    private String accessToken;

    @NotBlank
    @Schema(description = "OAuth 인가서버를 제공하는 서비스 이름 ex) google, kakao",
            example = "kakao")
    private String tokenType;
}
