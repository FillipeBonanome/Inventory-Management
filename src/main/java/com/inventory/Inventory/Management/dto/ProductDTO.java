package com.inventory.Inventory.Management.dto;

import com.inventory.Inventory.Management.domain.Product;

public record ProductDTO(
        Long id,
        String name,
        int quantity,
        String description,
        Long userId
) {

    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getQuantity(), product.getDescription(), product.getUser().getId());
    }

}
