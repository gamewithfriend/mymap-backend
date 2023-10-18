package com.sillimfive.mymap.web.dto.token.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class GooglePayload {

    private String sub;
    private String email;

    public GooglePayload(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }
}
