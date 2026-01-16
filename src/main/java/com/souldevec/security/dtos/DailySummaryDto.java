package com.souldevec.security.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DailySummaryDto {
    private LocalDate date;
    private BigDecimal totalEfectivo = BigDecimal.ZERO;
    private BigDecimal totalYape = BigDecimal.ZERO;
    private BigDecimal totalSnacks = BigDecimal.ZERO;
    private BigDecimal totalConsumo = BigDecimal.ZERO;
    private BigDecimal totalRetiros = BigDecimal.ZERO;
    private BigDecimal totalIngresoInventario = BigDecimal.ZERO;
    private Integer totalUsuarios = 0;
    private BigDecimal kwConsumidos = BigDecimal.ZERO;
    private BigDecimal totalDineroPancafe = BigDecimal.ZERO;
    private BigDecimal totalUsanzaPancafe = BigDecimal.ZERO;
    private BigDecimal totalIngresos = BigDecimal.ZERO;
    private BigDecimal totalGastos = BigDecimal.ZERO;
    private BigDecimal diferenciaDia = BigDecimal.ZERO;
    private BigDecimal ratioKw = BigDecimal.ZERO;
}
