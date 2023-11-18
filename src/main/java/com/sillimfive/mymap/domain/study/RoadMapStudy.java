package com.sillimfive.mymap.domain.study;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class RoadMapStudy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_study_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String memo;

    @OneToMany(mappedBy = "roadMapStudy")
    private List<RoadMapStudyNode> roadMapStudyNodes = new ArrayList<>();

    public RoadMapStudy(User user, RoadMap roadMap, String memo) {
        this.user = user;
        this.roadMap = roadMap;
        this.memo = memo;
    }

    public void addStudyNode(List<RoadMapNode> nodes) {
        List<RoadMapStudyNode> studyNodes =
                nodes.stream()
                        .map(node -> new RoadMapStudyNode(node.getId(), this, ""))
                        .collect(Collectors.toList());

        this.roadMapStudyNodes.addAll(studyNodes);
    }
}
