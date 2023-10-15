package com.sillimfive.mymap.web;

import com.sillimfive.mymap.config.jwt.TokenProvider;
import com.sillimfive.mymap.service.TokenService;
import com.sillimfive.mymap.web.dto.CreateAccessTokenRequest;
import com.sillimfive.mymap.web.dto.CreateAccessTokenResponse;
import com.sillimfive.mymap.web.dto.Error;
import com.sillimfive.mymap.web.dto.ResultSet;
import com.sillimfive.mymap.web.dto.user.UserDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

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

    @PostMapping
    public JSONObject oauth(String accessToken, String tokenType) throws IOException {
        JSONObject json = new JSONObject();

        String email = "mun1103.dev@gmail.com";

        //todo: @승환 관련로직 확인 및 추가 필!
        /*
         * successFullHandler 에 따라 생성함. 14, 1
         */

        Date now = new Date();
        Duration access = Duration.ofDays(1);
        Duration refresh = Duration.ofDays(14);

        String accessTkn = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + access.toMillis()))
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, "mymap")
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refresh.toMillis()))
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, "mymap")
                .compact();

        UserDto userDto = new UserDto();
        userDto.setNickname("sample");
        userDto.setEmail(email);

        json.put("accessToken", accessTkn);
        json.put("refreshToken", refreshToken);
        json.put("user", userDto);

        return json;
    }


    @GetMapping("/test/kakao")
    public String kakao() {
        String url = new StringBuilder("https://kauth.kakao.com/oauth/authorize?response_type=code")
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)).toString();

        return url;
    }
}
