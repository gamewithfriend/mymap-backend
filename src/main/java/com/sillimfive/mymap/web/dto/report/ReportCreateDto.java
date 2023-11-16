package com.sillimfive.mymap.web.dto.report;

import com.sillimfive.mymap.domain.Report;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReportCreateDto {

    @Schema(example = "코드번호 report01:  로드맵 , report02: 로드맵 댓글 ")
    private String reportType;
    @Schema(example = "좋은 아침을 맞이 하고자 로드맵을 들어 갔더니 욕설이 써 있는게 아니 겠어요!!!")
    private String content;
    @Schema(example = "reportType 01이면 타겟 roadmap_id, 02이면 reply_id")
    private Long reportTargetId;

    public Report  convert(User reporter, String reportType, String content, RoadMap roadMap, RoadMapReply roadMapReply) {

        return Report.createReport(reporter,reportType,content,roadMap, roadMapReply);

    }
}
