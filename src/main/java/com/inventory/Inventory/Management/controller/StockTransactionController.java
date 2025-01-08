package com.inventory.Inventory.Management.controller;

import com.inventory.Inventory.Management.domain.StockTransaction;
import com.inventory.Inventory.Management.dto.StockTransactionDTO;
import com.inventory.Inventory.Management.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @GetMapping("/{id}")
    public ResponseEntity<StockTransactionDTO> getTransaction(@PathVariable Long id) {
        StockTransactionDTO transactionDTO = stockTransactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<StockTransactionDTO> registerTransaction(@RequestBody StockTransactionDTO transactionDTO) {
        StockTransactionDTO stockTransactionDTO = stockTransactionService.registerTransaction(transactionDTO);
        return ResponseEntity.ok(stockTransactionDTO);
    }

}
