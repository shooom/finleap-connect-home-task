package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.incidentReports.dto.ReportDto;
import com.switchkit.switchkit_test.incidentReports.dto.ReportUpdateDto;
import com.switchkit.switchkit_test.security.jwt.JwtUser;
import com.switchkit.switchkit_test.users.UserService;
import com.switchkit.switchkit_test.users.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportsService {

    private final ReportsRepository repository;
    private final UserService userService;

    public Report createReport(ReportDto dto) {
        var userFromJwt = getUserFromJwt();
        var report = new Report(userService.findUser(userFromJwt.getId()), dto.getTitle());

        checkAndSetAssignee(dto.getAssigneeId(), report);
        return repository.save(report);
    }

    public List<Report> getReports() {
        return repository.findAll();
    }

    public Report updateReport(Long id, ReportUpdateDto dto) {
        var userFromJwt = getUserFromJwt();
        var report = repository.getById(id);

        if (userFromJwt.getId() != report.getAuthor().getId()) {
            if (report.getAssignee() != null && userFromJwt.getId() != report.getAssignee().getId()) {
                throw new RuntimeException("Only Author or Assignee can change the report");
            }
        }
        report.setTitle(dto.getTitle());
        checkAndSetAssignee(dto.getAssigneeId(), report);
        return repository.save(report);
    }

    public void closeReport(Long id) {
        var report = repository.getById(id);
        var userFromJwt = getUserFromJwt();

        if(userFromJwt.getId() != report.getAssignee().getId()) {
            throw new RuntimeException("Only Assignee can change status of the report");
        }
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

    private JwtUser getUserFromJwt() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean isAssigneeNotHaveActiveReports(Long id) {
        return repository.findAllByAssigneeIdAndStatus(id, Status.ASSIGNED).isEmpty();
    }

    private void checkAndSetAssignee(Long assigneeId, Report report) {
        if (assigneeId != null) {
            var newAssignee = getAssignee(assigneeId);
            report.setAssignee(newAssignee);
            report.setStatus(Status.ASSIGNED);
        }
    }
}
