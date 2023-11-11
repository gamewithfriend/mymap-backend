package com.sillimfive.mymap.domain;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private RoadMapReply roadMapReply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User reporter;

    private String content;
    private String reportType;

    @Builder
    public Report(String content, String reportType, RoadMap roadMap ,User reporter, RoadMapReply roadMapReply){
        this.content = content;
        this.reportType = reportType;
        this.roadMap = roadMap;
        this.reporter = reporter;
    }

    public static Report createReport(User reporter,String content, String reportType, RoadMap roadMap, RoadMapReply roadMapReply) {

        return Report.builder()
                .reporter(reporter)
                .roadMap(roadMap)
                .roadMapReply(roadMapReply)
                .content(content)
                .reportType(reportType)
                .build();

    }
}
