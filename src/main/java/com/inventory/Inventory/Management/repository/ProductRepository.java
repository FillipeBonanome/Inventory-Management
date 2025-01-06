package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long id);
}
