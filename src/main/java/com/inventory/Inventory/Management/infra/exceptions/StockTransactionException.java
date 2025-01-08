package com.inventory.Inventory.Management.infra.exceptions;

public class StockTransactionException extends RuntimeException {
    public StockTransactionException(String message) {
        super(message);
    }
}
