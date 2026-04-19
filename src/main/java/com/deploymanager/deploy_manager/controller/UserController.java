package com.deploymanager.deploy_manager.controller;

import com.deploymanager.deploy_manager.entity.user.dtos.CreateUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UpdatePasswordUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UpdateUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UserResponse;
import com.deploymanager.deploy_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> create (@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriComponentsBuilder){
        var newUser = service.createUser(request);
        var uri = uriComponentsBuilder.path("/api/users/{id}").buildAndExpand(newUser.id()).toUri();

        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll (@PageableDefault(size = 5) Pageable pageable){
        var user = service.getAll(pageable);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id){
        var user = service.getById(id);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update (@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request){
        var updateUser = service.update(id, request);

        return ResponseEntity.ok(updateUser);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponse> updatePassword (@Valid @PathVariable UUID id, @Valid @RequestBody UpdatePasswordUserRequest request){
        var updatePasswordUser = service.updatePassword(id, request);

        return ResponseEntity.ok(updatePasswordUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.softDelete(id);

        return ResponseEntity.noContent().build();
    }
}
