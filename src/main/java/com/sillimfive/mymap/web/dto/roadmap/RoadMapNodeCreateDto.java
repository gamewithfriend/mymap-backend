package com.sillimfive.mymap.web.dto.roadmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoadMapNodeCreateDto {
    private int order;
    private String content;
    private String title;
}
