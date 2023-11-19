package com.sillimfive.mymap.web.dto.roadmap.reply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoadMapReplyCreateDto {

    @NotBlank(message = "content can't be blank")
    @Schema(example = "로드맵 구려요.....")
    private String content;

    @Schema(example = "1")
    private Long parentId;
}
