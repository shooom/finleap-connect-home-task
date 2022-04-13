package com.switchkit.switchkit_test.incidentReports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportsRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByAssigneeIdAndStatus(Long assigneeId, Status status);
}
