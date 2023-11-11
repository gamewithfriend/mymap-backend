package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMapReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoadMapReply parent;

    @Enumerated(EnumType.STRING)
    private ReplyStatus replyStatus;

    private boolean deleteFlag;

    @Builder
    protected RoadMapReply(User user,RoadMap roadMap,String content,ReplyStatus replyStatus){
        this.user = user;
        this.roadMap = roadMap;
        this.content = content;
        this.replyStatus = replyStatus;
    }

    public static RoadMapReply createRoadMapReply(User user,RoadMap roadMap,String content,ReplyStatus replyStatus) {

        return RoadMapReply.builder()
                .user(user)
                .roadMap(roadMap)
                .content(content)
                .replyStatus(replyStatus)
                .build();
    }
}
