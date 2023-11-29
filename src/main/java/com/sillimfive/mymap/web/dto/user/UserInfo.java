package com.sillimfive.mymap.web.dto.user;

import com.sillimfive.mymap.domain.users.OAuthType;
import com.sillimfive.mymap.domain.users.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserInfo {

    @Schema(example = "mun1103.dev@gmail.com")
    private String email;
    @Schema(example = "마이맵!")
    private String nickName;

    @Schema(nullable = true , example = "1")
    private Long imageId;
    @Schema(nullable = true , example = "https://aws-northeast-mymap.s3.ap-northeast-2.amazonaws.com/roadmaps/202311051405511")
    private String imagePath;
    @Schema(example = "kakao")
    private OAuthType oAuthType;

    public UserInfo(User user) {
        // 유저 기본이미지 세팅시 삭제 처리
        if(user.getImage() != null){
            imageId = user.getImage().getId();
            imagePath = user.getImage().getPath();
        }else {
            imageId = null;
            imagePath = null;
        }
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.oAuthType = user.getOAuthType();
    }
}
