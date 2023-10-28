package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RoadMapLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

}
