package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
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

    @OneToMany(mappedBy = "roadMap")
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

    public static RoadMap createRoadMap(boolean hiddenFlag, User creator, Category category, String title, String description, Image image) {
        Assert.hasText(title, "title must not be empty");

        return RoadMap.builder()
                .hiddenFlag(hiddenFlag)
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

    public void addRoadMapTags(RoadMapTag... tags) {
        this.roadMapTags.addAll(Arrays.asList(tags));
        Arrays.stream(tags).forEach(tag -> tag.setRoadMap(this));
    }
}
