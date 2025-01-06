package com.inventory.Inventory.Management.repository;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.ProductDTO;
import com.inventory.Inventory.Management.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByCpf(String cpf);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByLoginAndIdNot(String login, Long id);
    boolean existsByCpfAndIdNot(String cpf, Long id);

    Page<User> findAllByActiveTrue(Pageable pageable);
}
