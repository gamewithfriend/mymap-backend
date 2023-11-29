package com.sillimfive.mymap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CodeResponseDto {

    @Schema(example = "alarm01")
    private String id;

    @Schema(example = "댓글 달림")
    private String value;
}
