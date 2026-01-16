package com.souldevec.security.services;

import com.souldevec.security.dtos.InventoryMovementResponseDto;
import com.souldevec.security.dtos.ProductHistoryDto;
import com.souldevec.security.entities.InventoryMovement;
import com.souldevec.security.entities.Product;
import com.souldevec.security.repositories.InventoryMovementRepository;
import com.souldevec.security.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPurchasePrice(productDetails.getPurchasePrice());
        product.setSellingPrice(productDetails.getSellingPrice());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        List<InventoryMovement> movements = inventoryMovementRepository.findByProduct(product);
        inventoryMovementRepository.deleteAll(movements);

        productRepository.deleteById(id);
    }

    public List<ProductHistoryDto> getProductHistory() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductHistoryDto).collect(Collectors.toList());
    }

    public BigDecimal getTotalInventoryValue() {
        return productRepository.findAll().stream()
                .map(product -> product.getSellingPrice().multiply(new BigDecimal(product.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProductHistoryDto mapToProductHistoryDto(Product product) {
        ProductHistoryDto dto = new ProductHistoryDto();
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setCurrentStock(product.getStock());

        List<InventoryMovement> movements = inventoryMovementRepository.findByProductOrderByTimestampDesc(product);
        List<InventoryMovementResponseDto> movementDtos = movements.stream().map(movement -> {
            InventoryMovementResponseDto movementDto = new InventoryMovementResponseDto();
            movementDto.setProductName(movement.getProduct().getName());
            movementDto.setQuantity(movement.getQuantity());
            movementDto.setType(movement.getType());
            movementDto.setPrice(movement.getProduct().getSellingPrice());
            movementDto.setTotalPrice(movement.getProduct().getSellingPrice().multiply(new java.math.BigDecimal(movement.getQuantity())));
            movementDto.setStockAfterMovement(movement.getStockAfterMovement());
            movementDto.setTimestamp(movement.getTimestamp());
            return movementDto;
        }).collect(Collectors.toList());

        dto.setMovements(movementDtos);
        return dto;
    }
}