package com.sillimfive.mymap.web.dto.user;

import com.sillimfive.mymap.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class UserDto {
    @Schema(example = "1")
    private Long id;
    @NotBlank
    @Schema(example = "mymap@gmail.com")
    private String email;
    @NotBlank
    @Schema(example = "mymap")
    private String nickname;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickName();
    }
}
