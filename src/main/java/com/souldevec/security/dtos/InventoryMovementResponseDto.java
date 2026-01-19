package com.souldevec.security.dtos;

import com.souldevec.security.enums.MovementType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class InventoryMovementResponseDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private MovementType type;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private Integer stockAfterMovement;
    private LocalDateTime timestamp;
}
