package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.TokenService;
import com.sillimfive.mymap.web.dto.CreateAccessTokenRequest;
import com.sillimfive.mymap.web.dto.CreateAccessTokenResponse;
import com.sillimfive.mymap.web.dto.Error;
import com.sillimfive.mymap.web.dto.ResultSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

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

}
