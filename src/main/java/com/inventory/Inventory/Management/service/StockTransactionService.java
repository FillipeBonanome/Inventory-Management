package com.inventory.Inventory.Management.service;

import com.inventory.Inventory.Management.domain.Product;
import com.inventory.Inventory.Management.domain.StockTransaction;
import com.inventory.Inventory.Management.domain.TransactionType;
import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.ProductDTO;
import com.inventory.Inventory.Management.dto.StockTransactionDTO;
import com.inventory.Inventory.Management.infra.exceptions.StockTransactionException;
import com.inventory.Inventory.Management.repository.ProductRepository;
import com.inventory.Inventory.Management.repository.StockTransactionRepository;
import com.inventory.Inventory.Management.repository.UserRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

        Optional<User> existingUser = userRepository.findById(transactionDTO.userId());

        if (existingUser.isEmpty()) {
            throw new StockTransactionException("User not found");
        }

        User user = userRepository.getReferenceById(transactionDTO.userId());

        Product product = productRepository.getReferenceById(transactionDTO.productId());

        if (!user.isActive()) {
            throw new StockTransactionException("User is not active");
        }

        if (!Objects.equals(user.getId(), product.getUser().getId())) {
            throw new StockTransactionException("You don't have this product");
        }

        int multiplier = 1;

        //Only outbound for now
        if (transactionDTO.type() == TransactionType.OUTBOUND) {
            multiplier = -1;
        }

        if (Math.abs(transactionDTO.quantity()) > product.getQuantity() && multiplier == -1) {
            throw new StockTransactionException("Product quantity insufficient");
        }

        double totalValue = Math.abs(product.getPrice() * transactionDTO.quantity());


        StockTransaction stockTransaction = new StockTransaction(null,
                product,
                transactionDTO.quantity() * multiplier,
                transactionDTO.date(),
                transactionDTO.type(),
                totalValue * multiplier,
                user
        );

        StockTransaction savedTransition = stockTransactionRepository.save(stockTransaction);
        product.setQuantity(product.getQuantity() + transactionDTO.quantity() * multiplier);

        if (product.getQuantity() < product.getMinQuantity()) {
            //Sending RabbitMQ Message to Queue
            System.out.println("Sending rabbitMQ message");
            Message message = new Message(("Stock of " + product.getName() + " is lower than " + product.getMinQuantity() + " item(s)").getBytes());
            ProductDTO productDTO = new ProductDTO(product);
            rabbitTemplate.convertAndSend("stock.low", productDTO);
        }

        return new StockTransactionDTO(savedTransition);
    }


}
