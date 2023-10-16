package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoadMapResponseDto {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private String imagePath;
}
