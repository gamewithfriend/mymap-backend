package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Optional;

@Data
public class RoadMapNodeResponseDto {
    @Schema(example = "1")
    private Long roadMapId;
    @Schema(example = "1")
    private Long nodeId;
    @Schema(nullable = true, example = "null")
    private Long parentNodeId;
    private int order;
    @Schema(example = "EntityManger 에 의한 영속성 관리")
    private String content;
    @Schema(example = "JPA 동작원리")
    private String title;

    public RoadMapNodeResponseDto(RoadMapNode node) {
        this.roadMapId = node.getRoadMap().getId();
        this.nodeId = node.getId();
        this.parentNodeId =
                Optional.ofNullable(node.getParent()).isPresent() ? node.getParent().getId() : null;
        this.content = node.getNodeContent();
        this.title = node.getNodeTitle();
        this.order = node.getNodeOrder();
    }
}
