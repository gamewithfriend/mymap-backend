package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.tag.Tag;
import com.sillimfive.mymap.web.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class RoadMapResponseDto {
    private Long id;
    private List<RoadMapNodeResponseDto> roadMapNodes;
    private UserDto user;
    private UserDto creator;
    private String category;
    private List<Tag> tags;
    private String title;
    private String description;
    private String imageDownloadPath;

    public RoadMapResponseDto(RoadMap roadMap) {
        this.id = roadMap.getId();
        this.roadMapNodes = roadMap.getRoadMapNodes().stream()
                                .map(RoadMapNodeResponseDto::new)
                                .collect(Collectors.toList());
        this.user = new UserDto(roadMap.getUser());
        this.creator = new UserDto(roadMap.getCreator());
        this.category = roadMap.getCategory().getName();
        this.title = roadMap.getTitle();
        this.description = roadMap.getDescription();
        this.imageDownloadPath = roadMap.getImage().getPath();
    }

    public void addTags(List<Tag> tags) {
        this.tags = tags;
    }
}
