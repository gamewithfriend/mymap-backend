package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class RoadMapSearch {

    @Schema(nullable = true, example = "1", description = "작성하지 않아도 된다.")
    private Long categoryId;

    @Schema(nullable = true, example = "1", description = "사용자 ID")
    private Long userId;

    @Schema(hidden = true)
    private boolean studyFlag;

    @Builder
    public RoadMapSearch(Long categoryId, Long userId, Boolean studyFlag) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.studyFlag = studyFlag;
    }
}
