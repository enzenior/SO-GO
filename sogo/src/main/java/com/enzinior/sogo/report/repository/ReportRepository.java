package com.enzinior.sogo.report.repository;

import com.enzinior.sogo.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
