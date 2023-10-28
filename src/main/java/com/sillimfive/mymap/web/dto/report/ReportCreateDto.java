package com.sillimfive.mymap.web.dto.report;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ReportCreateDto {
    private Long id;
    private String reportType;
    private String content;

    public ReportCreateDto(Report report) {
        this.id = report.getId();
        this.reportType = report.getReportType();
        this.content = report.getContent();
    }
}
