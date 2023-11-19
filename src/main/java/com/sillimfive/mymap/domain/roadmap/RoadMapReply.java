package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RoadMapReply parent;

    @Enumerated(EnumType.STRING)
    private ReplyStatus replyStatus;

    private boolean deleteFlag;

    @Builder
    protected RoadMapReply(User user,RoadMap roadMap,String content,RoadMapReply parent,ReplyStatus replyStatus,Boolean deleteFlag){
        this.user = user;
        this.roadMap = roadMap;
        this.content = content;
        this.parent = parent;
        this.replyStatus = replyStatus;
        this.deleteFlag = deleteFlag;
    }

    public static RoadMapReply createRoadMapReply(User user,RoadMap roadMap,String content,RoadMapReply parent,ReplyStatus replyStatus,Boolean deleteFlag) {

        return RoadMapReply.builder()
                .user(user)
                .roadMap(roadMap)
                .content(content)
                .parent(parent)
                .replyStatus(replyStatus)
                .deleteFlag(deleteFlag)
                .build();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void deleteRoadMapRelpy() {
        this.deleteFlag = true;
    }

    public void changeReplyisContributeReply() {
        this.replyStatus = ReplyStatus.reply02;
    }
}
