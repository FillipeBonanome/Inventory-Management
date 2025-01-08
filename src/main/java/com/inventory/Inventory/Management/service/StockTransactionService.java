package com.inventory.Inventory.Management.service;

import com.inventory.Inventory.Management.domain.Product;
import com.inventory.Inventory.Management.domain.StockTransaction;
import com.inventory.Inventory.Management.dto.StockTransactionDTO;
import com.inventory.Inventory.Management.infra.exceptions.StockTransactionException;
import com.inventory.Inventory.Management.repository.ProductRepository;
import com.inventory.Inventory.Management.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private ProductRepository productRepository;

    public StockTransactionDTO getTransactionById(Long id) {
        Optional<StockTransaction> transaction = stockTransactionRepository.findById(id);

        if (transaction.isEmpty()) {
            throw new StockTransactionException("Transaction not found");
        }

        return new StockTransactionDTO(transaction.get());
    }

    public StockTransactionDTO registerTransaction(StockTransactionDTO transactionDTO) {
        Optional<Product> existingProduct = productRepository.findById(transactionDTO.productId());

        if (existingProduct.isEmpty()) {
            throw new StockTransactionException("Product not found");
        }

        Product product = productRepository.getReferenceById(transactionDTO.productId());

        if (transactionDTO.quantity() < 0 && Math.abs(transactionDTO.quantity()) > product.getQuantity()) {
            throw new StockTransactionException("Product quantity insufficient");
        }

        StockTransaction stockTransaction = new StockTransaction(null,
                product,
                transactionDTO.quantity(),
                transactionDTO.date(),
                transactionDTO.type()
        );

        StockTransaction savedTransition = stockTransactionRepository.save(stockTransaction);
        product.setQuantity(product.getQuantity() + transactionDTO.quantity());

        return new StockTransactionDTO(savedTransition);
    }


}
