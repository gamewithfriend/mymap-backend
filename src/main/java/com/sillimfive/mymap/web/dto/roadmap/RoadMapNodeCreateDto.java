package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoadMapNodeCreateDto {
    @Schema(example = "0")
    private int order;

    @Schema(example = "Persistence managed by EntityManager")
    private String nodeContent;

    @Schema(example = "JPA 기본 - 동작원리")
    private String nodeTitle;
}
