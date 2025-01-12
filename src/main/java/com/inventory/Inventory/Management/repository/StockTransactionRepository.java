package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findAllByUserId(Long id);
}
