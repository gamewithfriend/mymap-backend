package com.sillimfive.mymap.domain.roadmap;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RoadMapNodeHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_node_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_history_id")
    private RoadMapHistory roadMapHistory;

    private int nodeOrder;
    private String nodeContent;
    private String nodeTitle;
}
