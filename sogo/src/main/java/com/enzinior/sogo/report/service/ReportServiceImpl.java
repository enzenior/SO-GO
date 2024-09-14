package com.enzinior.sogo.report.service;

import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.notification.service.NotificationService;
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.repository.ReportRepository;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import com.enzinior.sogo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final NotificationService notificationService;
    private final UserService userService;
    private final ReportRepository reportRepository;

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
        User user = userService.findUser(report.getUser().getUserUuid());

        report.setUser(user);
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report completeReport(Long reportId) {
        Report report = findReportById(reportId);
        report.changeProcessed();

        String comment = "신고 처리가 완료되었습니다.";
        notificationService.createNotification(report.getUser(), comment);

        return report;
    }

    private Report findReportById(long reportId) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        return optionalReport
                .orElseThrow( () -> new BusinessLogicException(ExceptionCode.REPORT_NOT_FOUND));
    }
}