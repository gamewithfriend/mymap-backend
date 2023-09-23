package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoadMapNode extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_node_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    private int nodeOrder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoadMapNode parent;

    // todo : content length 조건을 어떻게 할지 확인 필요
    private String content;
    private String title;

    private boolean deleteFlag;

}
