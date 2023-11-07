package com.sillimfive.mymap.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfo {

    @Schema(example = "mun1103.dev@gmail.com")
    private String email;
    @Schema(example = "마이맵!")
    private String nickName;
    @Schema(example = "1")
    private Long imageId;
    @Schema(example = "https://aws-northeast-mymap.s3.ap-northeast-2.amazonaws.com/roadmaps/202311051405511")
    private String imagePath;
    @Schema(example = "kakao")
    private String oAuthType;
}
