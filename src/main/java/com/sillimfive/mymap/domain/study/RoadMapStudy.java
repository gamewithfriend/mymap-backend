package com.sillimfive.mymap.domain.study;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String memo;

    @OneToMany(mappedBy = "roadMapStudy")
    private List<RoadMapStudyNode> roadMapStudyNodes = new ArrayList<>();

}
