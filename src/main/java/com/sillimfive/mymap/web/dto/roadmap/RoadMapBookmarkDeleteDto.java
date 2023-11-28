package com.sillimfive.mymap.web.dto.roadmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class RoadMapBookmarkDeleteDto {
    @JsonProperty("RoadMapBookmarkList")
    @ArraySchema(schema = @Schema(example = "1"))
    private List<@Min(value = 1) Long> roadMapBookmarkList;

}
