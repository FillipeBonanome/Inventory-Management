package com.inventory.Inventory.Management.dto;

import com.inventory.Inventory.Management.domain.User;

public record UserDTO(
        Long id,
        String login,
        String password,
        String name,
        String email,
        String cpf,
        boolean active
) {
    public UserDTO(User savedUser) {
        this(savedUser.getId(), savedUser.getLogin(), savedUser.getPassword(), savedUser.getName(), savedUser.getEmail(), savedUser.getCpf(), savedUser.isActive());
    }
}
