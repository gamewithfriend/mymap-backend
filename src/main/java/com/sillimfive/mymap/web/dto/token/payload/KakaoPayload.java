package com.sillimfive.mymap.web.dto.token.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KakaoPayload {

    private String id;
    private KakaoAccount kakao_account;
}

