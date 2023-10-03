package com.sillimfive.mymap.service.dto;

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
