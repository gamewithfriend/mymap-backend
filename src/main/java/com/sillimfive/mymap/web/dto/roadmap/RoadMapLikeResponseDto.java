package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.Alarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoadMapLikeResponseDto {
    @Schema(example = "생성시 roadMapLikeId 삭제시 0")
    private Long roadMapLikeId;

    public RoadMapLikeResponseDto(Long roadMapLikeId) {
        this.roadMapLikeId = roadMapLikeId;
    }
}
