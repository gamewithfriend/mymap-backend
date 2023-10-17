package com.sillimfive.mymap.web.dto.roadmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoadMapNodeCreateDto {
    @JsonProperty("order")
    private int order;
    @Schema(example = "Persistence managed by EntityManager")
    @JsonProperty("nodeContent")
    private String nodeContent;
    @Schema(example = "JPA 기본 - 동작원리")
    @JsonProperty("nodeTitle")
    private String nodeTitle;
}
