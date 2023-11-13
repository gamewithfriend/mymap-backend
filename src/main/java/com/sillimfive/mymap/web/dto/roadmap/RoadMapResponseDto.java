package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.web.dto.CategoryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RoadMapResponseDto {
    private Long id;
    private String title;
    private String description;
    @Schema(example = "categoryInfo")
    private CategoryDto categoryDto;
    private String imagePath;

    public RoadMapResponseDto(RoadMap roadMap) {
        this.id = roadMap.getId();
        this.title = roadMap.getTitle();
        this.description = roadMap.getDescription();
        this.categoryDto = new CategoryDto(roadMap.getCategory().getId(), roadMap.getCategory().getName(), roadMap.getCategory().getParent().getId());
        this.imagePath = roadMap.getImage().getPath();
    }
}
