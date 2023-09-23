package com.sillimfive.mymap.domain.tag;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoadMapTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private boolean deleteFlag;

}
