package com.sillimfive.mymap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoadMapNodeCreateDto {
    private int index;
    private Integer parentIndex;
    private String content;
    private String title;
}
