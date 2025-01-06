package com.inventory.Inventory.Management.service;

import com.inventory.Inventory.Management.domain.Product;
import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.ProductDTO;
import com.inventory.Inventory.Management.infra.exceptions.ProductException;
import com.inventory.Inventory.Management.repository.ProductRepository;
import com.inventory.Inventory.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ProductDTO registerProduct(ProductDTO productDTO) {

        Optional<User> user = userRepository.findById(productDTO.userId());

        if (user.isEmpty()) {
            throw new ProductException("User not found for this product");
        }

        Product product = new Product(productDTO.id(), productDTO.name(), productDTO.quantity(), productDTO.description(), user.get());
        Product savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductException("Product not found");
        }

        return new ProductDTO(product.get());
    }

    public ProductDTO deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductException("Product not found");
        }

        productRepository.deleteById(id);
        return new ProductDTO(product.get());

    }

}
