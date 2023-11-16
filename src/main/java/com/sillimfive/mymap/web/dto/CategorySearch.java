package com.sillimfive.mymap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategorySearch {
    @Schema(example = "false")
    private boolean rootFlag;
    @Schema(example = "1")
    private Long parentId;
}