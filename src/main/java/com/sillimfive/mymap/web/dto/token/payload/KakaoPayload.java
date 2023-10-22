package com.sillimfive.mymap.web.dto.token.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayload {

    private String id;
    private KakaoAccount kakao_account;
}

