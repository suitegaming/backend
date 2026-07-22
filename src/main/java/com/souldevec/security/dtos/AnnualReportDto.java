package com.souldevec.security.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AnnualReportDto {
    private int year;
    private BigDecimal totalDineroPcsAño = BigDecimal.ZERO;
    private BigDecimal totalDineroPancafeAño = BigDecimal.ZERO;
    private BigDecimal totalUsanzaPancafeAño = BigDecimal.ZERO;
    private BigDecimal totalEfectivoAño = BigDecimal.ZERO;
    private BigDecimal totalYapeAño = BigDecimal.ZERO;
    private BigDecimal totalSnacksAño = BigDecimal.ZERO;
    private BigDecimal totalRetirosAño = BigDecimal.ZERO;
    private BigDecimal totalGastosAño = BigDecimal.ZERO;
    private BigDecimal totalKwConsumidosAño = BigDecimal.ZERO;
    private BigDecimal promedioRatioKwAño = BigDecimal.ZERO;
    private BigDecimal balanceAño = BigDecimal.ZERO;
    private Integer totalUsuariosAño = 0;
    private List<MonthSummaryDto> monthlySummaries = new ArrayList<>();

    public AnnualReportDto() {}

    public AnnualReportDto(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getTotalDineroPcsAño() {
        return totalDineroPcsAño;
    }

    public void setTotalDineroPcsAño(BigDecimal totalDineroPcsAño) {
        this.totalDineroPcsAño = totalDineroPcsAño;
    }

    public BigDecimal getTotalDineroPancafeAño() {
        return totalDineroPancafeAño;
    }

    public void setTotalDineroPancafeAño(BigDecimal totalDineroPancafeAño) {
        this.totalDineroPancafeAño = totalDineroPancafeAño;
    }

    public BigDecimal getTotalUsanzaPancafeAño() {
        return totalUsanzaPancafeAño;
    }

    public void setTotalUsanzaPancafeAño(BigDecimal totalUsanzaPancafeAño) {
        this.totalUsanzaPancafeAño = totalUsanzaPancafeAño;
    }

    public BigDecimal getTotalEfectivoAño() {
        return totalEfectivoAño;
    }

    public void setTotalEfectivoAño(BigDecimal totalEfectivoAño) {
        this.totalEfectivoAño = totalEfectivoAño;
    }

    public BigDecimal getTotalYapeAño() {
        return totalYapeAño;
    }

    public void setTotalYapeAño(BigDecimal totalYapeAño) {
        this.totalYapeAño = totalYapeAño;
    }

    public BigDecimal getTotalSnacksAño() {
        return totalSnacksAño;
    }

    public void setTotalSnacksAño(BigDecimal totalSnacksAño) {
        this.totalSnacksAño = totalSnacksAño;
    }

    public BigDecimal getTotalRetirosAño() {
        return totalRetirosAño;
    }

    public void setTotalRetirosAño(BigDecimal totalRetirosAño) {
        this.totalRetirosAño = totalRetirosAño;
    }

    public BigDecimal getTotalGastosAño() {
        return totalGastosAño;
    }

    public void setTotalGastosAño(BigDecimal totalGastosAño) {
        this.totalGastosAño = totalGastosAño;
    }

    public BigDecimal getTotalKwConsumidosAño() {
        return totalKwConsumidosAño;
    }

    public void setTotalKwConsumidosAño(BigDecimal totalKwConsumidosAño) {
        this.totalKwConsumidosAño = totalKwConsumidosAño;
    }

    public BigDecimal getPromedioRatioKwAño() {
        return promedioRatioKwAño;
    }

    public void setPromedioRatioKwAño(BigDecimal promedioRatioKwAño) {
        this.promedioRatioKwAño = promedioRatioKwAño;
    }

    public BigDecimal getBalanceAño() {
        return balanceAño;
    }

    public void setBalanceAño(BigDecimal balanceAño) {
        this.balanceAño = balanceAño;
    }

    public Integer getTotalUsuariosAño() {
        return totalUsuariosAño;
    }

    public void setTotalUsuariosAño(Integer totalUsuariosAño) {
        this.totalUsuariosAño = totalUsuariosAño;
    }

    public List<MonthSummaryDto> getMonthlySummaries() {
        return monthlySummaries;
    }

    public void setMonthlySummaries(List<MonthSummaryDto> monthlySummaries) {
        this.monthlySummaries = monthlySummaries;
    }
}
