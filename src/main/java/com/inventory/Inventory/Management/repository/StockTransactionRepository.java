package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findAllByUserId(Long id);
}
