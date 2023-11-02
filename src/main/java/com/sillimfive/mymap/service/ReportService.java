package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Report;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.repository.CodeRepository;
import com.sillimfive.mymap.repository.ReportRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final RoadMapRepository roadMapRepository;

    public Long create(String content,String reportType,Long roadMapId,Long reporterId ) {

        RoadMap roadMap = roadMapRepository.findById(roadMapId).get();
        User reporter = userRepository.findById(reporterId).get();
        Report report = Report.createReport(content, reportType,roadMap,reporter);
        report = reportRepository.save(report);
        return report.getId();
    }



}
