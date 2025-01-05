package com.inventory.Inventory.Management.infra.exceptions;

public class UserException extends RuntimeException {
  public UserException(String message) {
    super(message);
  }
}
