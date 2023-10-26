package com.sillimfive.mymap.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TempController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;



    @Operation(hidden = true)
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<?> getAccessToKakaoTEST(HttpServletRequest request) throws IOException {
        Map<String, String> responseMap = new HashMap<>();

        String code = request.getParameter("code");

        String url = "https://kauth.kakao.com/oauth/token";


        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod(RequestMethod.POST.toString());
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        StringBuffer requestBody = new StringBuffer();
        String redirectUrl = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        requestBody.append("code=").append(URLEncoder.encode(code, StandardCharsets.UTF_8))
                .append("&grant_type=").append(URLEncoder.encode("authorization_code", StandardCharsets.UTF_8))
                .append("&redirect_uri=").append(redirectUrl)
                .append("&client_id=").append(URLEncoder.encode(clientId, StandardCharsets.UTF_8));
//                    .append("&client_secret=").append(clientSecret)

        out.writeBytes(requestBody.toString());
        out.flush();
        out.close();

//        con.setConnectTimeout(5000);
//        con.setConnectTimeout(5000);

        int status = con.getResponseCode();
        System.out.println("status for token = " + status);

        String res = "";
        if (status == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputReader = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);

            try {
                BufferedReader lineReader = new BufferedReader(inputReader);
                StringBuffer sb = new StringBuffer();

                String line;
                while ((line = lineReader.readLine()) != null) {
                    sb.append(line);
                }
                res = sb.toString();

            } catch (IOException e) {
                throw new RuntimeException("fail to read API");
            }
        }
        System.out.println("response = " + res);
        ResponseDto responseDto = new ObjectMapper().readValue(res, ResponseDto.class);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(hidden = true)
    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<?> getAuthorizationCodeForGoogleTest(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");

        String param = googleClientId + ":" + googleClientSecret;
        String authorization = Base64.getEncoder().encodeToString(param.getBytes());
        String url = "https://oauth2.googleapis.com/token";

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod(RequestMethod.POST.toString());
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", "Basic " + authorization);

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        StringBuffer requestBody = new StringBuffer();
        requestBody
                .append("code=").append(code)
                .append("&client_id=").append(googleClientId)
                .append("&client_secret").append(googleClientSecret)
                .append("&grant_type=").append("authorization_code")
                .append("&redirect_uri=").append(googleRedirectUri);

        System.out.println("url = " + url + ", body = " + requestBody);

        out.writeBytes(requestBody.toString());
        out.flush();
        out.close();

        int status = con.getResponseCode();
        System.out.println("status for token = " + status);

        StringBuffer sb = new StringBuffer();
        if (status == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputReader = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);

            try {
                BufferedReader lineReader = new BufferedReader(inputReader);

                String line;
                while ((line = lineReader.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                throw new RuntimeException("fail to read API");
            }
        }
        System.out.println("response = " + sb);
        Map<String, String> response = new ObjectMapper().readValue(sb.toString(), Map.class);

        return ResponseEntity.ok(response);
    }

    @Getter @Setter
    static class ResponseDto {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("id_token")
        private String idToken;
        @JsonProperty("refresh_token_expires_in")
        private String refreshTokenExpiresIn;
        @JsonProperty("scope")
        private String scope;
    }
}
