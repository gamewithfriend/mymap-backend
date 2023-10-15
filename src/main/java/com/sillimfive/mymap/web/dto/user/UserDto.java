package com.sillimfive.mymap.web.dto.user;

import com.sillimfive.mymap.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class UserDto {
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickName();
    }
}
