package com.sillimfive.mymap.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CategoryDto {
    @Min(value = 1)
    private Long id;
    @Schema(example = "백엔드")
    private String name;
}
