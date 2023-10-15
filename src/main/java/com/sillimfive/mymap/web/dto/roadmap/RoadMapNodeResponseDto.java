package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import lombok.Data;

import java.util.Optional;

@Data
public class RoadMapNodeResponseDto {
    private Long roadMapId;
    private Long nodeId;
    private Long parentNodeId;
    private int order;
    private String content;
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
