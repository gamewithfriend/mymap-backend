package com.sillimfive.mymap.web.dto;

import com.sillimfive.mymap.domain.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ImageResponseDto {
    @Schema(example = "https://aws-northeast-mymap.s3.ap-northeast-2.amazonaws.com/roadmaps/202311051405511")
    private String path;
    @Schema(example = "1")
    private Long imageId;

    public ImageResponseDto(Image image) {
        this.path = image.getPath();
        this.imageId = image.getId();
    }
}
