package com.souldevec.security.controllers;

import com.souldevec.security.dtos.MonthlyReportDto;
import com.souldevec.security.dtos.ProductSalesDto;
import com.souldevec.security.dtos.SnackProfitDto;
import com.souldevec.security.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/best-sellers")
    public ResponseEntity<List<ProductSalesDto>> getBestSellingProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<ProductSalesDto> report = reportService.getBestSellingProducts(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/snack-profit")
    public ResponseEntity<SnackProfitDto> getSnackProfit(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        SnackProfitDto report = reportService.getSnackProfit(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}

