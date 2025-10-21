package com.souldevec.security.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class YapeDetalleDto {
    private String detalle;
    private BigDecimal monto;
}
