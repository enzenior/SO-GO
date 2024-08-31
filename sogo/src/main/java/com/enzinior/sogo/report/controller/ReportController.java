package com.enzinior.sogo.report.controller;

//import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.report.dto.ReportDto;
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.mapper.ReportMapper;
import com.enzinior.sogo.report.service.ReportService;
import com.enzinior.sogo.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportMapper reportMapper;
    private final NotificationService notificationService;

    // 신고 전체 조회(미처리 프론트에서 필터)
    @GetMapping("")
    public ResponseEntity<?> findReports() {
        List<Report> reports = reportService.findReports();

        return ResponseEntity.ok(reportMapper.reportsToReportDtos(reports));
    }

    // 신고 상세 조회
    @GetMapping("/{report_id}")
    public ResponseEntity<?> findReport(@PathVariable("report_id") Long reportId) {
        Report report = reportService.findReport(reportId);

        return ResponseEntity.ok(reportMapper.reportToReportResponse(report));
    }

    // 신고하기
    @PostMapping("")
    public ResponseEntity<?> addReport(@RequestBody ReportDto.Post reportDto) {
        Report report = reportMapper.reportPostToReport(reportDto);
        Report result = reportService.postReport(report);

        return ResponseEntity.ok().build();
    }

    // 신고 처리 완료
    @DeleteMapping("/{report_id}")
    public ResponseEntity<?> completeReport(@PathVariable("report_id") Long reportId) {
        Report report = reportService.completeReport(reportId);

        String comment = "신고 처리가 완료되었습니다.";
        notificationService.createNotification(report.getUser(), comment);

        return ResponseEntity.ok(reportMapper.reportToReportResponse(report));
    }

}
