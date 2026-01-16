package com.souldevec.security.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesDto {
    private Long productId;
    private String productName;
    private Long totalQuantitySold;
}
