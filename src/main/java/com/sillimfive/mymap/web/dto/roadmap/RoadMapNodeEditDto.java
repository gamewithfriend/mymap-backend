package com.sillimfive.mymap.web.dto.roadmap;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoadMapNodeEditDto {
    private Long id;
    private int order;
    private String nodeContent;
    private String nodeTitle;
}
