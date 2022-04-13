package com.switchkit.switchkit_test.incidentReports.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ReportDto {
    private Long authorId;
    @Nullable
    private Long assigneeId;
    private String title;
}
