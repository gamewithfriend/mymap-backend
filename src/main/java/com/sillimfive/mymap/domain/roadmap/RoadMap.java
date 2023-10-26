package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeEditDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMap extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long id;

    private boolean hiddenFlag;

    @OneToMany(mappedBy = "roadMap", cascade = CascadeType.ALL)
    private List<RoadMapNode> roadMapNodes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "roadMap", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<RoadMapTag> roadMapTags = new ArrayList<>();

    private String title;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    private boolean deleteFlag;

    @Builder
    protected RoadMap(boolean hiddenFlag, User creator, Category category, String title, String description, Image image) {
        this.hiddenFlag = hiddenFlag;
        this.user = creator;
        this.creator = creator;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public static RoadMap createRoadMap(User creator, Category category, String title, String description, Image image) {
        Assert.hasText(title, "title must not be empty");

        return RoadMap.builder()
                .creator(creator)
                .category(category)
                .title(title)
                .description(description)
                .image(image)
                .build();
    }

    public void addRoadMapNodes(List<RoadMapNode> nodes) {
        this.roadMapNodes.addAll(nodes);
        nodes.forEach(node -> node.setRoadMap(this));
    }

    public void addRoadMapTags(List<RoadMapTag> tags) {
        this.roadMapTags.addAll(tags);
        tags.forEach(tag -> tag.setRoadMap(this));
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeImage(Image image) {
        this.image = image;
    }

    /**
     * 로드맵의 제목이나 설명이 변경되었다면 true 반환.
     * 그렇지 않다면 false 반환
     *
     * @param title
     * @param description
     * @return true if title or description has been changed, or false
     */
    public boolean changeContents(String title, String description) {
        if (!this.title.equals(title) || !this.description.equals(description)) {
            this.title = title;
            this.description = description;
            return true;
        }

        return false;
    }

    /**
     * 로드맵의 노드트리의 구성이 변경되었다면 true 반환.
     * 그렇지 않다면 false 반환
     *
     * @param nodeDtoList
     * @return
     */
    public boolean changeNodeTree(List<RoadMapNodeEditDto> nodeDtoList) {
        boolean changedFlag = false;
        List<RoadMapNode> deleteList = new ArrayList<>();
        deleteList.addAll(roadMapNodes);

        Collections.sort(nodeDtoList, Comparator.comparing(RoadMapNodeEditDto::getId));

        for (RoadMapNodeEditDto nodeDto : nodeDtoList) {
            if (nodeDto.getId().equals(-1)) {
                RoadMapNode node = RoadMapNode.builder()
                                .nodeOrder(nodeDto.getOrder())
                                .nodeTitle(nodeDto.getNodeTitle())
                                .nodeContent(nodeDto.getNodeContent())
                                .build();
                roadMapNodes.add(nodeDto.getOrder(), node);
                changedFlag = true;
            }
            else {
                for (RoadMapNode node : roadMapNodes) {
                    if (!nodeDto.getId().equals(node.getId())) continue;

                    boolean isOrderChanged = node.changeNodeOrder(nodeDto.getOrder());
                    boolean isDetailChanged = node.changeNodeDetail(nodeDto.getNodeTitle(), nodeDto.getNodeContent());

                    changedFlag = isOrderChanged || isDetailChanged ? true : changedFlag;

                    deleteList.remove(node);
                }
            }
        }

        if (roadMapNodes.size() != deleteList.size()) changedFlag = true;

        return changedFlag;
    }

    public void updateRoadMapTags(List<Long> roadMapTagIdList, List<RoadMapTag> newTags) {
        List<RoadMapTag> removeList = new ArrayList<>();
        for (RoadMapTag roadMapTag : roadMapTags) {
            if (roadMapTagIdList.contains(roadMapTag.getId())) continue;

            removeList.add(roadMapTag);
        }
        roadMapTags.remove(removeList);
        roadMapTags.addAll(newTags);
    }
}
