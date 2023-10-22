package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.TokenService;
import com.sillimfive.mymap.web.dto.Error;
import com.sillimfive.mymap.web.dto.ResultSet;
import com.sillimfive.mymap.web.dto.token.AuthenticationTokenRequest;
import com.sillimfive.mymap.web.dto.token.AuthenticationTokenResponse;
import com.sillimfive.mymap.web.dto.token.CreateAccessTokenRequest;
import com.sillimfive.mymap.web.dto.token.CreateAccessTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Tag(name = "Authentication", description = "token generation API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth/token")
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Operation(summary = "로그인 토큰 발급",
                description = "<b>Receives tokens (accessToken, refreshToken) and userInfo</b><br>" +
                            "OAuth 인증을 통해 Authorization Server에서 받은 access 토큰과 토큰의 유형(구글/카카오)을 전달하여" +
                            "현 Application의 인증에 필요한 토큰 발급")
    @PostMapping
    public ResponseEntity<AuthenticationTokenResponse> OauthAuthenticateToken(@RequestBody AuthenticationTokenRequest tokenRequest){

        AuthenticationTokenResponse authTokenResponse =
                tokenService.getAuthTokenResponse(tokenRequest.getAccessToken(), tokenRequest.getTokenType());

        return ResponseEntity.ok(authTokenResponse);
    }

    @Operation(summary = "액세스 토큰 갱신", description = "Refresh access token (desc)")
    @PostMapping("/renew")
    public ResultSet createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request
    ){
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        Error error = Error.builder()
                .code(HttpStatus.CREATED)
                .message("토큰 발급 성공 ").build();

        return ResultSet.builder()
                .error(error)
                .data(new CreateAccessTokenResponse(newAccessToken))
                .result("SUCCESS")
                .build();
    }

    @GetMapping("/test/kakao")
    public String kakao() {
        String url = new StringBuilder("https://kauth.kakao.com/oauth/authorize?response_type=code")
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)).toString();

        return url;
    }

    @GetMapping("/test/google")
    public String google(){
        String url = new StringBuilder("https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=https%3A//www.googleapis.com/auth/drive.metadata.readonly&" +
                "access_type=offline&" +
                "include_granted_scopes=true&" +
                "response_type=code")
                .append("&redirect_uri=").append(URLEncoder.encode(googleRedirectUri, StandardCharsets.UTF_8))
                .append("&client_id=").append(googleClientId).toString();
        return url;
    }

}
