package com.enzinior.sogo.report.service;

import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.repository.ReportRepository;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Report> findReports() {
        return reportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Report findReport(Long reportId) {
        return findReportById(reportId);
    }

    @Override
    @Transactional
    public Report postReport(Report report) {
        User user = userRepository.findByUserUuid(report.getUser().getUserUuid())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        report.setUser(user);
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report completeReport(Long reportId) {
        Report report = findReportById(reportId);
        report.changeProcessed();

        return report;
    }

    private Report findReportById(long reportId) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        return optionalReport
                .orElseThrow( () -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
    }
}