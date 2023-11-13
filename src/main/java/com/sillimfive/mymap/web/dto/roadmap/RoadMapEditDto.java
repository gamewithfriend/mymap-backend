package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class RoadMapEditDto {

    @Min(value = 1)
    private Long imageId;

    @NotBlank
    private String title;
    private String description;

    @Min(value = 1)
    private Long categoryId;

    @ArraySchema(schema = @Schema(example = "jpa"))
    private List<@NotBlank String> tags;

    @ArraySchema(minItems = 1, schema = @Schema(implementation = RoadMapNodeEditDto.class))
    private List<RoadMapNodeEditDto> nodeDtoList;

    @Schema(hidden = true)
    public boolean hasNewTags() {
        return tags != null && !tags.isEmpty();
    }
}
