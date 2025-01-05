package com.inventory.Inventory.Management.validation;

import com.inventory.Inventory.Management.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserValidation {
    void validate(UserDTO userDTO);
}
