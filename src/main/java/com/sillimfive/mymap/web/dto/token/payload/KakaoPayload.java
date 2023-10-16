package com.sillimfive.mymap.web.dto.token.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayload {

    private String id;
    private KakaoAccount kakao_account;
}

