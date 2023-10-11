package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.TokenService;
import com.sillimfive.mymap.web.dto.CreateAccessTokenRequest;
import com.sillimfive.mymap.web.dto.CreateAccessTokenResponse;
import com.sillimfive.mymap.web.dto.Error;
import com.sillimfive.mymap.web.dto.ResultSet;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Tag(name = "Authentication", description = "token generation API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;


    @PostMapping("/api/token")
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

    /**
     * oauth 인증 후 리다이렉트를 통해 토큰을 받기 위해 생성한 임시 메서드
     * @return
     */
    @GetMapping("oauth/login/response/temp")
    public JSONObject responseOauthToken(String refresh, String access) {
        JSONObject json = new JSONObject();
        json.put("refresh", refresh);
        json.put("access", access);

        return json;
    }


    @GetMapping("oauth/login/kakao")
    public String kakao() {
        String url = new StringBuilder("https://kauth.kakao.com/oauth/authorize?response_type=code")
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)).toString();

        return url;
    }
}
