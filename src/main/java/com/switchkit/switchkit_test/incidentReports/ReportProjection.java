package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.users.proections.UserProjection;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(
        name = "planeReport",
        types = {Report.class, UserProjection.class}
)
public interface ReportProjection {
    long getId();
    String getTitle();
    UserProjection getAuthor();
    UserProjection getAssignee();
    String getStatus();
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
