package com.sillimfive.mymap.web.dto.study;

import com.sillimfive.mymap.web.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class RoadMapStudyDetailDto {
    // 로드맵 학습 객체 자체에 메모를 등록하는 건 추가하지 않음.

    // RoadMap fields
    @Schema(example = "1")
    private Long roadMapId;
    private UserDto user;
    private UserDto origin;
    private Long categoryId;
    @Schema(example = "JPA")
    private String categoryName;
    @Schema(example = "JPA 기본편 완전 정복")
    private String title;
    private String description;
    @Min(1) @Schema(example = "1")
    private Long imageId;
    @Schema(example = "https://aws-northeast-mymap.s3.ap-northeast-2.amazonaws.com/ROADMAPS/202310222336511")
    private String imageDownloadPath;
    @Schema(example = "DEFAULT")
    private String theme;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<RoadMapStudyNodeDetailDto> roadMapStudyNodeList;

}
