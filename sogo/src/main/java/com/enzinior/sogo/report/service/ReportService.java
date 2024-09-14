package com.enzinior.sogo.report.service;

import com.enzinior.sogo.report.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> findReports();

    Report findReport(Long reportId);

    Report completeReport(Long reportId);

    Report postReport(Report report);
}