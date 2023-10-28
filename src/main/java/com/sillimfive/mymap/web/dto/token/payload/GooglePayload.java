package com.sillimfive.mymap.web.dto.token.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GooglePayload {

    @JsonProperty("id")
    private String sub;
    private String email;

    public GooglePayload(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }
}
