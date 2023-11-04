package com.sillimfive.mymap.domain.study;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMapStudyNode implements Persistable<Long> {

    /**
     * RoadMapStudy를 통해 연결되는 RoadMap의 Node ID를 가져온다.
     */
    @Id
    @Column(name = "roadmap_node_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_study_id")
    private RoadMapStudy roadMapStudy;

    private String memo;

    @Transient
    private boolean isCreated;

    //todo: check true or false | percentage presentation
    private boolean completeFlag;

    @Override
    public boolean isNew() {
        return isCreated;
    }

    public RoadMapStudyNode(Long id, RoadMapStudy roadMapStudy, String memo) {
        this.id = id;
        this.roadMapStudy = roadMapStudy;
        this.memo = memo;
        this.isCreated = true;
    }
}
