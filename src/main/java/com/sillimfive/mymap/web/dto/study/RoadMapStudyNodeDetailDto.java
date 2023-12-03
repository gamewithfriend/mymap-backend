package com.sillimfive.mymap.web.dto.study;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class RoadMapStudyNodeDetailDto {
    @Schema(example = "1")
    private Long roadMapId;
    @Schema(example = "1")
    private Long nodeId;
    private int order;
    @Schema(example = "EntityManager에 의한 영속성 관리")
    private String content;
    @Schema(example = "JPA 동작원리")
    private String title;

    private String memo;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

}
