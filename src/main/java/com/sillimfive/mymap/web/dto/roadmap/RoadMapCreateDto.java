package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private String title;
    private String description;

    private List<RoadMapNodeCreateDto> nodeDtoList;

    @NotNull
    private Long categoryId;
    private List<Long> tagIds;
    private List<String> newTags;

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
                .title(rootDto.getTitle())
                .content(rootDto.getContent())
                .build();
        nodeList.add(root);

        for (int i=1; i<nodeDtoList.size(); i++) {
            RoadMapNodeCreateDto nodeDto = nodeDtoList.get(i);

            RoadMapNode node = RoadMapNode.builder()
                    .title(nodeDto.getTitle())
                    .parent(nodeList.get(i-1))
                    .content(nodeDto.getContent())
                    .build();

            nodeList.add(node);
        }

        return nodeList;
    }
}
