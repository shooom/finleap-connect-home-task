package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.incidentReports.dto.ReportDto;
import com.switchkit.switchkit_test.incidentReports.dto.ReportUpdateDto;
import com.switchkit.switchkit_test.utils.ProjectionsConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportsController {

    private final ReportsService service;
    private final ProjectionsConverter converter;

    @PostMapping
    public ResponseEntity<ReportProjection> createReport(@RequestBody ReportDto dto) {
        var newReport = service.createReport(dto);
        return ResponseEntity.ok(converter.getProjection(ReportProjection.class, newReport));
    }

    @GetMapping
    public ResponseEntity<List<ReportProjection>> getReports() {
        var reports = service.getReports();

        return ResponseEntity.ok(reports.stream()
                .map(report -> converter.getProjection(ReportProjection.class, report))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportProjection> updateReport(@PathVariable Long id, @RequestBody ReportUpdateDto dto) {
        var updatedReport = service.updateReport(id, dto);
        return ResponseEntity.ok(converter.getProjection(ReportProjection.class, updatedReport));
    }

    @GetMapping("/{id}/close")
    public void updateStatus(@PathVariable Long id) {
        service.closeReport(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        // TODO: check if principal is author of assignee
        service.deleteReport(id);
    }
}
