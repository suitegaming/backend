package com.souldevec.security.dtos;

import java.math.BigDecimal;

public class MonthSummaryDto {
    private int month;
    private String monthName;
    private BigDecimal totalDineroPcs = BigDecimal.ZERO;
    private BigDecimal totalDineroPancafe = BigDecimal.ZERO;
    private BigDecimal totalUsanzaPancafe = BigDecimal.ZERO;
    private BigDecimal totalEfectivo = BigDecimal.ZERO;
    private BigDecimal totalYape = BigDecimal.ZERO;
    private BigDecimal totalSnacks = BigDecimal.ZERO;
    private BigDecimal totalRetiros = BigDecimal.ZERO;
    private BigDecimal totalGastos = BigDecimal.ZERO;
    private BigDecimal totalKwConsumidos = BigDecimal.ZERO;
    private BigDecimal promedioRatioKw = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private Integer totalUsuarios = 0;

    public MonthSummaryDto() {}

    public MonthSummaryDto(int month, String monthName) {
        this.month = month;
        this.monthName = monthName;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public BigDecimal getTotalDineroPcs() {
        return totalDineroPcs;
    }

    public void setTotalDineroPcs(BigDecimal totalDineroPcs) {
        this.totalDineroPcs = totalDineroPcs;
    }

    public BigDecimal getTotalDineroPancafe() {
        return totalDineroPancafe;
    }

    public void setTotalDineroPancafe(BigDecimal totalDineroPancafe) {
        this.totalDineroPancafe = totalDineroPancafe;
    }

    public BigDecimal getTotalUsanzaPancafe() {
        return totalUsanzaPancafe;
    }

    public void setTotalUsanzaPancafe(BigDecimal totalUsanzaPancafe) {
        this.totalUsanzaPancafe = totalUsanzaPancafe;
    }

    public BigDecimal getTotalEfectivo() {
        return totalEfectivo;
    }

    public void setTotalEfectivo(BigDecimal totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    public BigDecimal getTotalYape() {
        return totalYape;
    }

    public void setTotalYape(BigDecimal totalYape) {
        this.totalYape = totalYape;
    }

    public BigDecimal getTotalSnacks() {
        return totalSnacks;
    }

    public void setTotalSnacks(BigDecimal totalSnacks) {
        this.totalSnacks = totalSnacks;
    }

    public BigDecimal getTotalRetiros() {
        return totalRetiros;
    }

    public void setTotalRetiros(BigDecimal totalRetiros) {
        this.totalRetiros = totalRetiros;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public BigDecimal getTotalKwConsumidos() {
        return totalKwConsumidos;
    }

    public void setTotalKwConsumidos(BigDecimal totalKwConsumidos) {
        this.totalKwConsumidos = totalKwConsumidos;
    }

    public BigDecimal getPromedioRatioKw() {
        return promedioRatioKw;
    }

    public void setPromedioRatioKw(BigDecimal promedioRatioKw) {
        this.promedioRatioKw = promedioRatioKw;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(Integer totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
}
