package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.incidentReports.dto.ReportDto;
import com.switchkit.switchkit_test.incidentReports.dto.ReportUpdateDto;
import com.switchkit.switchkit_test.users.UserService;
import com.switchkit.switchkit_test.users.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportsService {

    private final ReportsRepository repository;
    private final UserService userService;

    public Report createReport(ReportDto dto) {
        var report = new Report(userService.findUser(dto.getAuthorId()), dto.getTitle());

        if (dto.getAssigneeId() != null) {
            var assignee = getAssignee(dto.getAssigneeId());
            report.setAssignee(assignee);
            report.setStatus(Status.ASSIGNED);
        }
        return repository.save(report);
    }

    public List<Report> getReports() {
        return repository.findAll();
    }

    public Report updateReport(Long id, ReportUpdateDto dto) {
        var report = repository.getById(id);
        report.setTitle(dto.getTitle());

        if (dto.getAssigneeId() != null && report.getAssignee().getId() != dto.getAssigneeId()) {
            var newAssignee = getAssignee(dto.getAssigneeId());
            report.setAssignee(newAssignee);
        }
        return repository.save(report);
    }

    public void closeReport(Long id) {
        var report = repository.getById(id);
        report.setStatus(Status.CLOSED);
        repository.save(report);
    }

    public void deleteReport(Long id) {
        var report = repository.getById(id);

        if (report.getStatus().equals(Status.ASSIGNED)) {
            throw new RuntimeException("Can't delete report in status [ASSIGNED]");
        }
        repository.delete(report);
    }

    private User getAssignee(Long id) {
        var assignee = userService.findUser(id);
        if (assignee.isAccountNonLocked() && isAssigneeNotHaveActiveReports(id)) {
            return assignee;
        } else {
            throw new RuntimeException("Can't to join this user as Assignee");
        }
    }

    private boolean isAssigneeNotHaveActiveReports(Long id) {
        return repository.findAllByAssigneeIdAndStatus(id, Status.ASSIGNED).isEmpty();
    }
}
