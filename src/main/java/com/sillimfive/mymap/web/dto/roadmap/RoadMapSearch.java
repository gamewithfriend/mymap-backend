package com.sillimfive.mymap.web.dto.roadmap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class RoadMapSearch {

    @Schema(nullable = true, example = "1", description = "작성하지 않아도 된다.")
    private Long categoryId;

}
