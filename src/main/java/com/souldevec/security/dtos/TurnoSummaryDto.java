package com.souldevec.security.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TurnoSummaryDto {
    private TurnoResponseDto turno;
    private List<InventoryMovementResponseDto> movements;
    private List<RetiroDetalleDto> retiros;
    private List<YapeDetalleDto> yapes;
    private BigDecimal totalSales;
}
