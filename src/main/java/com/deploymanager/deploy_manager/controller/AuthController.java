package com.deploymanager.deploy_manager.controller;

import com.deploymanager.deploy_manager.config.TokenService;
import com.deploymanager.deploy_manager.entity.user.User;
import com.deploymanager.deploy_manager.entity.user.dtos.auth.AuthDTO;
import com.deploymanager.deploy_manager.entity.user.dtos.auth.AuthResponse;
import com.deploymanager.deploy_manager.entity.user.dtos.user.CreateUserRequestDTO;
import com.deploymanager.deploy_manager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO authDto){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generatedToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid CreateUserRequestDTO request){
        var newUser = authService.register(request);

        return ResponseEntity.ok().body(newUser);
    }

}