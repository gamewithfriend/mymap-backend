package com.sillimfive.mymap.web.dto.roadmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
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
    @NotBlank(message = "title can't be blank")
    @Schema(example = "JPA 학습 로드맵")
    @JsonProperty("title")
    private String title;

    @Schema(example = "Back-end DB access skill")
    @JsonProperty("description")
    private String description;

    @JsonProperty("nodeDtoList")
    private List<RoadMapNodeCreateDto> nodeDtoList;

    @NotNull @Min(value = 1)
    @Schema(example = "1")
    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("tagIds")
    private List<@Min(value = 1) Long> tagIds;

    @Schema
    @JsonProperty("newTags")
    private List<@NotBlank String> newTags;

    @Schema(hidden = true)
    public RoadMap convert(User user, Category category, Image image) {

        return RoadMap.createRoadMap(user, category, title, description, image);
    }

    @Schema(hidden = true)
    public List<RoadMapNode> getRoadMapNodesFromDto() {
        Collections.sort(nodeDtoList, Comparator.comparing(node -> Integer.valueOf(node.getOrder())));

        List<RoadMapNode> nodeList = new ArrayList<>();

        RoadMapNodeCreateDto rootDto = nodeDtoList.get(0);
        RoadMapNode root = RoadMapNode.builder()
                .nodeOrder(0)
                .nodeTitle(rootDto.getNodeTitle())
                .nodeContent(rootDto.getNodeContent())
                .build();
        nodeList.add(root);

        for (int i=1; i<nodeDtoList.size(); i++) {
            RoadMapNodeCreateDto nodeDto = nodeDtoList.get(i);

            RoadMapNode node = RoadMapNode.builder()
                    .nodeOrder(i)
                    .parent(nodeList.get(i-1))
                    .nodeTitle(nodeDto.getNodeTitle())
                    .nodeContent(nodeDto.getNodeContent())
                    .build();

            nodeList.add(node);
        }

        return nodeList;
    }
}
