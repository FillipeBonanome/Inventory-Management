package com.inventory.Inventory.Management.infra.handler;

import com.inventory.Inventory.Management.dto.ExceptionDTO;
import com.inventory.Inventory.Management.infra.exceptions.ProductException;
import com.inventory.Inventory.Management.infra.exceptions.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionDTO> handleUserException(UserException userException) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(userException.getMessage()));
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ExceptionDTO> handleProductException(ProductException productException) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(productException.getMessage()));
    }

}
