package com.sillimfive.mymap.web.dto.user;

import com.sillimfive.mymap.domain.User;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserDto {
    private Long id;
    private String email;
    private String nickname;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickName();
    }
}
