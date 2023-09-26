package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMapTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private boolean deleteFlag;

    protected void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }

    public static List<RoadMapTag> createRoadMapTags(List<Tag> tags) {
        List<RoadMapTag> roadMapTagList = new ArrayList<>();
        for (Tag tag : tags) {
            RoadMapTag roadMapTag = new RoadMapTag();
            roadMapTag.tag = tag;
            roadMapTagList.add(roadMapTag);

            tag.countIncrease();
        }

        return roadMapTagList;
    }
}
