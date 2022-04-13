package com.switchkit.switchkit_test.incidentReports;

import com.switchkit.switchkit_test.incidentReports.dto.ReportDto;
import com.switchkit.switchkit_test.incidentReports.dto.ReportUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportsController {

    private final ReportsService service;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody ReportDto dto) {
        var newReport = service.createReport(dto);
        return ResponseEntity.ok(newReport);
    }

    @GetMapping
    public ResponseEntity<List<Report>> getReports() {
        return ResponseEntity.ok(service.getReports());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody ReportUpdateDto dto) {
        // TODO: check if principal is author of assignee
        var updatedReport = service.updateReport(id, dto);
        return ResponseEntity.ok(updatedReport);
    }

    @GetMapping("/{id}/close")
    public void updateStatus(@PathVariable Long id) {
        // TODO: check if principal is assignee
        service.closeReport(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        // TODO: check if principal is author of assignee
        service.deleteReport(id);
    }
}
