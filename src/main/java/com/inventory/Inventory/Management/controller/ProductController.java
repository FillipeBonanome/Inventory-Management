package com.inventory.Inventory.Management.controller;

import com.inventory.Inventory.Management.dto.ProductDTO;
import com.inventory.Inventory.Management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProductDTO> registerProduct(@RequestBody ProductDTO productResponseDTO) {
        ProductDTO product = productService.registerProduct(productResponseDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        ProductDTO product = productService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }
}
