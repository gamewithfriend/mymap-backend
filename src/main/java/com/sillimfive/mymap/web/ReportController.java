package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.service.ReportService;
import com.sillimfive.mymap.web.dto.report.ReportCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "Report API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "신고하기", description = "report")
    @PostMapping
    public ResponseEntity<?> report(Authentication authentication, ReportCreateDto reportCreateDto) {
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        Long reporterId = user.getId();
        Long roadMapId = reportCreateDto.getId();
        String reportType = reportCreateDto.getReportType();
        String content = reportCreateDto.getContent();
        reportService.create(content, reportType, roadMapId, reporterId);
        return ResponseEntity.ok(json);
    }

}