package com.sillimfive.mymap.service.dto;

import lombok.Data;

@Data
public class RoadMapNodeCreateDto {
    private int index;
    private int parentIndex;
    private String content;
    private String title;
}
