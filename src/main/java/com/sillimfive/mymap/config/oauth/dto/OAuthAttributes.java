package com.sillimfive.mymap.config.oauth.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Getter
@Slf4j
public class OAuthAttributes {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes){
        log.info("registrationId ={}", registrationId);
        log.info("userNameAttributeName ={}", userNameAttributeName);
        log.info("attributes ={}", attributes);
        if("kakao".equals(registrationId)){
            return ofKakao(userNameAttributeName, attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);

    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String,Object> attributes){
        log.info("userNameAttributeName ={}", userNameAttributeName);
        log.info("attributes ={}", attributes);
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    //카카오 생성자
    private static OAuthAttributes ofKakao(String userNameAttributeName,
                                           Map<String,Object> attributes){
        log.info("userNameAttributeName ={}", userNameAttributeName);
        log.info("attributes ={}", attributes);
        Map<String, Object> response = (Map<String, Object>)attributes.get("kakao_account");

        return OAuthAttributes.builder()
//                .name((String)response.get("name"))
                .name("test")
                .email((String)response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


}
