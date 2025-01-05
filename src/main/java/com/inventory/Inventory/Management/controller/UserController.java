package com.inventory.Inventory.Management.controller;

import com.inventory.Inventory.Management.dto.UserDTO;
import com.inventory.Inventory.Management.repository.UserRepository;
import com.inventory.Inventory.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUsers(@PathVariable Long id) {
        UserDTO user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO, UriComponentsBuilder builder) {
        UserDTO newUser = userService.registerUser(userDTO);
        return ResponseEntity.created(builder.path("/users/{id}").buildAndExpand(newUser.id()).toUri()).body(newUser);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        UserDTO deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> restoreUser(@PathVariable Long id) {
        UserDTO restoredUser = userService.restoreUserById(id);
        return ResponseEntity.ok(restoredUser);
    }

}
