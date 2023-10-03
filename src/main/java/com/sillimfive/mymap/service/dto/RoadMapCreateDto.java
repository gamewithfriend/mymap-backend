package com.sillimfive.mymap.service.dto;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
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
    private boolean hiddenFlag;
    private String title;
    private String description;

    private List<RoadMapNodeCreateDto> nodeDtoList;

    private Long categoryId;
    private Long imageId;
    private List<Long> tagIds;
    private List<String> newTags;

    public RoadMap convert(User user, Category category, Image image) {

        return RoadMap.createRoadMap(hiddenFlag, user, category, title, description, image);
    }

    public List<RoadMapNode> getRoadMapNodesFromDto() {
        Collections.sort(nodeDtoList, Comparator.comparing(node -> Integer.valueOf(node.getIndex())));

        List<RoadMapNode> nodeList = new ArrayList<>();

        RoadMapNodeCreateDto rootDto = nodeDtoList.get(0);
        RoadMapNode root = RoadMapNode.builder()
                .nodeOrder(rootDto.getIndex())
                .title(rootDto.getTitle())
                .content(rootDto.getContent())
                .build();
        nodeList.add(root);

        for (int i=1; i<nodeDtoList.size(); i++) {
            RoadMapNodeCreateDto nodeDto = nodeDtoList.get(i);

            RoadMapNode node = RoadMapNode.builder()
                    .nodeOrder(nodeDto.getIndex())
                    .title(nodeDto.getTitle())
                    .content(nodeDto.getContent())
                    .build();

            if (nodeDto.getParentIndex().equals(nodeList.get(i-1).getNodeOrder()))
                node.setParentNode(nodeList.get(i-1));

            nodeList.add(node);
        }

        return nodeList;
    }
}
