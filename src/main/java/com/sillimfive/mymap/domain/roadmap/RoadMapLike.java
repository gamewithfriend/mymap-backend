package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    protected RoadMapLike(User user,RoadMap roadMap){
        this.user = user;
        this.roadMap = roadMap;
    }

    public static RoadMapLike createRoadMapLike(User user,RoadMap roadMap) {

        return RoadMapLike.builder()
                .user(user)
                .roadMap(roadMap)
                .build();
    }
}
