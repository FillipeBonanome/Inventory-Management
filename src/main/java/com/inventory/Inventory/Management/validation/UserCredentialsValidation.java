package com.inventory.Inventory.Management.validation;

import com.inventory.Inventory.Management.dto.UserDTO;
import com.inventory.Inventory.Management.infra.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsValidation implements UserValidation {

    @Override
    public void validate(UserDTO userDTO) {
        String login = userDTO.login();
        String password = userDTO.password();
        singleValidation(login);
        singleValidation(password);
    }

    public void singleValidation(String field) {
        if (field == null) {
            throw new UserException("Credentials can't be null");
        }

        if (field.length() < 8) {
            throw new UserException("Credentials must have at least 8 digits");
        }

        if (!field.matches(".*\\d.*")) {
            throw new UserException("Credentials must have at least 1 number");
        }
    }
}
