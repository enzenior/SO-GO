package com.enzinior.sogo.report.mapper;

import com.enzinior.sogo.report.dto.ReportDto;
import com.enzinior.sogo.report.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(source = "userUuid", target = "user.userUuid")
    Report reportPostToReport(ReportDto.Post report);

    @Mapping(source = "user.nickname", target = "userNickname")
    @Mapping(source = "user.userUuid", target = "userUuid")
    ReportDto.Response reportToReportResponse(Report report);

    List<ReportDto.Response> reportsToReportDtos(List<Report> reports);
}
