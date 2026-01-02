package com.souldevec.security.controllers;

import com.souldevec.security.dtos.MonthlyReportDto;
import com.souldevec.security.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyReportDto> getMonthlyReport(
            @RequestParam int year,
            @RequestParam int month) {
        
        MonthlyReportDto report = reportService.getMonthlyReport(year, month);
        return ResponseEntity.ok(report);
    }
}
