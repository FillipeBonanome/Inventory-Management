package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findAllByUserId(Long id);

    @Query("""
            SELECT t FROM StockTransaction t where t.transactionDate between :start and :end
            """)
    List<StockTransaction> findTransactionByInterval(@Param("start") LocalDate start,@Param("end") LocalDate end);
}
