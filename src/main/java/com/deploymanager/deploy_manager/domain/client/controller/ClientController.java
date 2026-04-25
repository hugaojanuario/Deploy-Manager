package com.deploymanager.deploy_manager.domain.client.controller;


import com.deploymanager.deploy_manager.domain.client.dtos.ClientPrivateResponseDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.CreateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.UpdateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity <ClientPrivateResponseDTO> create (@RequestBody @Valid CreateClientRequestDTO request, UriComponentsBuilder uriBuilder){
        var newClient = service.create(request);
        var uri = uriBuilder.path("/api/clients/{id}").buildAndExpand(newClient.id()).toUri();

        return ResponseEntity.created(uri).body(newClient);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 5) Pageable pageable, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(service.getAllForAdmin(pageable));
        }
        return ResponseEntity.ok(service.getAllForUser(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(service.getByIdForAdmin(id));
        }
        return ResponseEntity.ok(service.getByIdForUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity <ClientPrivateResponseDTO> update (@PathVariable UUID id, @RequestBody @Valid UpdateClientRequestDTO request, Authentication authentication){
        var user = service.update(id, request, authentication.getName());

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> softDelete (@PathVariable UUID id, Authentication authentication){
        service.softDelete(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
