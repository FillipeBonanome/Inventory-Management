package com.inventory.Inventory.Management.dto;

import com.inventory.Inventory.Management.domain.User;

import java.util.List;

public record UserDTO(
        Long id,
        String login,
        String password,
        String name,
        String email,
        String cpf,
        boolean active,
        List<ProductDTO> productList,
        List<StockTransactionDTO> transactionList
) {
    public UserDTO(User savedUser) {
        this(savedUser.getId(), savedUser.getLogin(), savedUser.getPassword(), savedUser.getName(), savedUser.getEmail(), savedUser.getCpf(), savedUser.isActive(), savedUser.getProductList().stream().map(ProductDTO::new).toList(), savedUser.getStockTransactions().stream().map(StockTransactionDTO::new).toList());
    }
}
