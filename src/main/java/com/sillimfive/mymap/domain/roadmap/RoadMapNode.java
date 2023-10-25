package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeEditDto;
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

    // todo : content length 조건을 어떻게 할지 확인 필요
    private String nodeContent;
    private String nodeTitle;

    @Builder
    public RoadMapNode(int nodeOrder, String nodeContent, String nodeTitle) {
        this.nodeOrder = nodeOrder;
        this.nodeContent = nodeContent;
        this.nodeTitle = nodeTitle;
    }

    protected void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }

    /**
     *
     * @param nodeDtoOrder
     * @return
     */
    protected boolean changeNodeOrder(int nodeDtoOrder) {
        if (this.nodeOrder != nodeDtoOrder) {
            this.nodeOrder = nodeDtoOrder;
            return true;
        }

        return false;
    }

    /**
     *
     * @param title
     * @param content
     * @return
     */
    protected boolean changeNodeDetail(String title, String content) {
        if (!this.nodeTitle.equals(title) || !this.nodeContent.equals(content)) {
            this.nodeTitle = title;
            this.nodeContent = content;
            return true;
        }

        return false;
    }
}
