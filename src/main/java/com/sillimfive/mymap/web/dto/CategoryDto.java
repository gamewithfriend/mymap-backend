package com.sillimfive.mymap.web.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDto {
    @Min(value = 1)
    private Long id;
    private String name;
}
