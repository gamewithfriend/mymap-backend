package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Report;
import com.sillimfive.mymap.domain.ReportType;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.report.ReportCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sillimfive.mymap.domain.QReport.report;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoadMapRepository roadMapRepository;
    private final RoadMapReplyRepository roadMapReplyRepository;

    public Long create(Long reporterId, ReportCreateDto reportCreateDto) {
        User reporter = userRepository.getReferenceById(reporterId);
        String reportType = reportCreateDto.getReportType();
        String content = reportCreateDto.getContent();
        Long reportTargetId = reportCreateDto.getReportTargetId();
        RoadMap roadMap = null;
        RoadMapReply roadMapReply = null;
        switch (reportType){
            case "report01": roadMap = roadMapRepository.getReferenceById(reportTargetId);
            case "report02": roadMapReply = roadMapReplyRepository.getReferenceById(reportTargetId);
        }

        Report report = reportCreateDto.convert(reporter, reportType, content, roadMap, roadMapReply);
        reportRepository.save(report);
        return report.getId();
    }



}
