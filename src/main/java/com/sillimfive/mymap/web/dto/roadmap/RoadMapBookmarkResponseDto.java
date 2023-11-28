package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoadMapBookmarkResponseDto {
    @Schema(example = "1")
    private Long roadMapId;

    @Schema(example = "조회 또는 등록시 roadmapBookmarkId 삭제 0")
    private Long roadmapBookmarkId;

    public RoadMapBookmarkResponseDto(Long roadMapId,Long roadmapBookmarkId) {
        this.roadmapBookmarkId = roadmapBookmarkId;
        this.roadMapId = roadMapId;
    }
}
