package com.sillimfive.mymap.web.dto.roadmap;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RoadMapUpdateDto {
    private boolean imageChanged;
    private Long imageId;

    private Long categoryId;
    private List<Long> tagIds;
    private List<String> newTags;

}
