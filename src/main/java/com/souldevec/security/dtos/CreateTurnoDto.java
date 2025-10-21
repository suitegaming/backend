package com.souldevec.security.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateTurnoDto {
    private TurnoDto turno;
    private List<InventoryMovementDto> movements;
    private List<RetiroDetalleDto> retiros;
    private List<YapeDetalleDto> yapes;
}
