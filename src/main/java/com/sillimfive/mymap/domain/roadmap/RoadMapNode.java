package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public RoadMapNode(int nodeOrder, RoadMapNode parent, String content, String title) {
        this.nodeOrder = nodeOrder;
        this.parent = parent;
        this.content = content;
        this.title = title;
    }

    protected void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }

    public void setParentNode(RoadMapNode parent) {
        this.parent = parent;
    }
}
