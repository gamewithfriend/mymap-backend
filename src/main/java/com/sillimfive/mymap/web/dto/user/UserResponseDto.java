package com.sillimfive.mymap.web.dto.user;

import com.sillimfive.mymap.domain.users.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private String email;
    private String nickname;

    @Builder
    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickName();
    }
}
