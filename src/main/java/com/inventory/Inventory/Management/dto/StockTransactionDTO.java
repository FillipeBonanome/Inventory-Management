package com.inventory.Inventory.Management.dto;

import com.inventory.Inventory.Management.domain.StockTransaction;
import com.inventory.Inventory.Management.domain.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

public record StockTransactionDTO(
        Long id,
        @NotNull
        Long productId,
        @NotNull
        int quantity,
        @Past
        LocalDate date,
        @NotBlank
        TransactionType type,
        @NotNull
        double value,
        @NotNull
        Long userId
) {
    public StockTransactionDTO(StockTransaction transaction) {
        this(transaction.getId(), transaction.getProduct().getId(), transaction.getQuantity(), transaction.getTransactionDate(), transaction.getTransactionType(), transaction.getTransactionValue(), transaction.getUser().getId());
    }
}
