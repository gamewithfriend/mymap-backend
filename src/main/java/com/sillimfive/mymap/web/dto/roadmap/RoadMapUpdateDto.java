package com.sillimfive.mymap.web.dto.roadmap;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RoadMapUpdateDto {
    private boolean imageChanged;
    @Min(value = 1)
    private Long imageId;
    @Min(value = 1)
    private Long categoryId;
    private List<@Min(value = 1) Long> roadMapTagIds;
    private List<@NotBlank String> newTags;

    private List<RoadMapNodeUpdateDto> nodeDtoList;

    @NotBlank
    private String title;
    private String description;
}
