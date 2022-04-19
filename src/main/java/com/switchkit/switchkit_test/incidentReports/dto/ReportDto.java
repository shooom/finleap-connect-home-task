package com.switchkit.switchkit_test.incidentReports.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ReportDto {
    @Nullable
    private Long assigneeId;
    private String title;
}
