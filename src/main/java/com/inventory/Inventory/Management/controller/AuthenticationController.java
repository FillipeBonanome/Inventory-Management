package com.inventory.Inventory.Management.controller;

import com.inventory.Inventory.Management.domain.User;
import com.inventory.Inventory.Management.dto.AuthenticationDTO;
import com.inventory.Inventory.Management.dto.TokenDTO;
import com.inventory.Inventory.Management.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        var token = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

}
