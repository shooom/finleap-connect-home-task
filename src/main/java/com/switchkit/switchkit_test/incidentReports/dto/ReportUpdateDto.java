package com.switchkit.switchkit_test.incidentReports.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ReportUpdateDto {
    @Nullable
    private Long assigneeId;
    private String title;
}
