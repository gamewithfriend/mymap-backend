package com.sillimfive.mymap.domain;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
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

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "roadmap_id")
    private RoadMap roadMap;

    // 도메인 추가 백엔드 팀원들과 상의 필요!!!!!!!
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User reporter;

    private String content;
    private String reportType;
    private LocalDateTime createdDate;

    @Builder
    public Report(String content, String reportType, RoadMap roadMap ,User reporter){
        this.content = content;
        this.reportType = reportType;
        this.roadMap = roadMap;
        this.reporter = reporter;
    }

    public static Report createReport(String content, String reportType, RoadMap roadMap,User reporter) {

        return Report.builder()
                .content(content)
                .reportType(reportType)
                .roadMap(roadMap)
                .reporter(reporter)
                .build();

    }
}
