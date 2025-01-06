package com.inventory.Inventory.Management.service;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.UserDTO;
import com.inventory.Inventory.Management.infra.exceptions.UserException;
import com.inventory.Inventory.Management.repository.UserRepository;
import com.inventory.Inventory.Management.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    List<UserValidation> userValidationList;

    public UserDTO registerUser(UserDTO userDTO) {

        //Chain of Responsibility
        userValidationList.forEach(v -> v.validate(userDTO));

        //Manually so don't skip an id, should refactor
        if(userRepository.existsByEmail(userDTO.email())) {
            throw new UserException("E-mail already registered");
        }

        if(userRepository.existsByCpf(userDTO.cpf())) {
            throw new UserException("CPF already registered");
        }

        if(userRepository.existsByLogin(userDTO.login())) {
            throw new UserException("Login already registered");
        }


        User user = new User(userDTO);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);

    }

    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UserException("User not found");
        }

        return new UserDTO(user.get());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new UserException("This user doesn't exist");
        }

        if (userRepository.existsByCpfAndIdNot(userDTO.cpf(), id)) {
            throw new UserException("CPF already registered");
        }

        if(userRepository.existsByEmailAndIdNot(userDTO.email(), id)) {
            throw new UserException("Email already registered");
        }

        if(userRepository.existsByLoginAndIdNot(userDTO.login(), id)) {
            throw new UserException("Login already registered");
        }

        User user = userRepository.getReferenceById(id);
        user.setPassword(userDTO.password());
        user.setCpf(userDTO.cpf());
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setLogin(userDTO.login());

        return new UserDTO(user);
    }

    public UserDTO deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        User userReference = userRepository.getReferenceById(id);
        userReference.setActive(false);
        return new UserDTO(userReference);
    }

    public UserDTO restoreUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        User userReference = userRepository.getReferenceById(id);
        userReference.setActive(true);
        return new UserDTO(userReference);
    }

    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public Page<UserDTO> getActiveUsers(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(UserDTO::new);
    }
}
