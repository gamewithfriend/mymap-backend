package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadMapLikeRequestDto {

    @Schema(example = "1")
    private Long roadmapId;
}
