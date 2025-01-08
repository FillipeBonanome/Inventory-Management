package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
}
