package com.sillimfive.mymap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequestDto {
    @Schema(example = "백엔드")
    private String name;
    @Schema(example = "null", nullable = true)
    private Long parentId;
}
