package com.souldevec.security.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MonthlyReportDto {
    private BigDecimal totalEfectivoMes = BigDecimal.ZERO;
    private BigDecimal totalYapeMes = BigDecimal.ZERO;
    private BigDecimal totalSnacksMes = BigDecimal.ZERO;
    private BigDecimal totalConsumoMes = BigDecimal.ZERO;
    private BigDecimal totalRetirosMes = BigDecimal.ZERO;
    private BigDecimal totalIngresoInventarioMes = BigDecimal.ZERO;
    private Integer totalUsuariosMes = 0;
    private BigDecimal totalKwConsumidosMes = BigDecimal.ZERO;
    private BigDecimal totalDineroPancafeMes = BigDecimal.ZERO;
    private BigDecimal totalUsanzaPancafeMes = BigDecimal.ZERO;
    private BigDecimal totalIngresosMes = BigDecimal.ZERO;
    private BigDecimal totalGastosMes = BigDecimal.ZERO;
    private BigDecimal diferenciaMes = BigDecimal.ZERO;
    private List<DailySummaryDto> dailySummaries;
}
