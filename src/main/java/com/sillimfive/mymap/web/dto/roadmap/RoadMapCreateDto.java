package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter @Setter
@ToString
public class RoadMapCreateDto {

    private Long imageId;

    @NotBlank(message = "title can't be blank")
    @Schema(example = "JPA 학습 로드맵")
    private String title;

    @Schema(example = "Back-end DB access skill")
    private String description;

    @ArraySchema(minItems = 1, schema = @Schema(implementation = RoadMapNodeCreateDto.class))
    private List<RoadMapNodeCreateDto> nodeDtoList = new ArrayList<>();

    @NotNull @Min(value = 1)
    @Schema(example = "1")
    private Long categoryId;

    @ArraySchema(schema = @Schema(example = "1"))
    private List<@Min(value = 1) Long> tagIds;

    @ArraySchema(schema = @Schema(example = "jpa"))
    private List<@NotBlank String> newTags;

    @Schema(hidden = true)
    public RoadMap convert(User user, Category category, Image image) {

        return RoadMap.createRoadMap(user, category, title, description, image);
    }

    @Schema(hidden = true)
    public List<RoadMapNode> getRoadMapNodesFromDto() {
        Collections.sort(nodeDtoList, Comparator.comparing(node -> Integer.valueOf(node.getOrder())));

        List<RoadMapNode> nodeList = new ArrayList<>();

        for (int i=0; i<nodeDtoList.size(); i++) {
            RoadMapNodeCreateDto nodeDto = nodeDtoList.get(i);

            RoadMapNode node = RoadMapNode.builder()
                    .nodeOrder(i)
                    .nodeTitle(nodeDto.getNodeTitle())
                    .nodeContent(nodeDto.getNodeContent())
                    .build();

            nodeList.add(node);
        }

        return nodeList;
    }
}
