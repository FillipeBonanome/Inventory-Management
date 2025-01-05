package com.inventory.Inventory.Management.validation;

import com.inventory.Inventory.Management.dto.UserDTO;
import com.inventory.Inventory.Management.infra.exceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class UserCPFValidation implements UserValidation {

    @Override
    public void validate(UserDTO user) {
        String cpf = user.cpf();
        if(cpf == null) {
            throw new UserException("CPF can't be null");
        }

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new UserException("Invalid CPF format");
        }

        int sum = 0;
        for(int i = 0; i < 9; i++) {
            sum = sum + (cpf.charAt(i) - '0') * (10 - i);
        }

        int module = 11 - (sum % 11);
        int firstDigit = (module == 10 || module == 11) ? 0 : module;

        sum = 0;
        for(int i = 0; i < 10; i++) {
            sum = sum + (cpf.charAt(i) - '0') * (11 - i);
        }

        module = 11 - (sum % 11);
        int secondDigit = (module == 10 || module == 11) ? 0 : module;

        if (cpf.charAt(9) - '0' != firstDigit || cpf.charAt(10) - '0' != secondDigit) {
            throw new UserException("Invalid CPF digits");
        }
    }
}
