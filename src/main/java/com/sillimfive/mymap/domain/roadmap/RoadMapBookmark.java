package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMapBookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    @Builder
    protected RoadMapBookmark(User user,RoadMap roadMap){
        this.user = user;
        this.roadMap = roadMap;
    }

    public static RoadMapBookmark createRoadMapBookmark(User user,RoadMap roadMap) {

        return RoadMapBookmark.builder()
                .user(user)
                .roadMap(roadMap)
                .build();
    }
}
