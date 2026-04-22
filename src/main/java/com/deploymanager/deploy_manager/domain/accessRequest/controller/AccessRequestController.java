package com.deploymanager.deploy_manager.domain.accessRequest.controller;

import com.deploymanager.deploy_manager.domain.accessRequest.dtos.AccessRequestResponseDTO;
import com.deploymanager.deploy_manager.domain.accessRequest.dtos.ApprovedAccessResponseDTO;
import com.deploymanager.deploy_manager.domain.accessRequest.dtos.CreateAccessRequestDTO;
import com.deploymanager.deploy_manager.domain.accessRequest.dtos.RejectAccessRequestDTO;
import com.deploymanager.deploy_manager.domain.accessRequest.enums.AccessStatus;
import com.deploymanager.deploy_manager.domain.accessRequest.service.AccessRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/access-requests")
public class AccessRequestController {

    private final AccessRequestService service;

    @PostMapping
    public ResponseEntity<AccessRequestResponseDTO> create(
            @RequestBody @Valid CreateAccessRequestDTO request,
            Authentication authentication,
            UriComponentsBuilder uriBuilder) {

        var newRequest = service.create(request, authentication.getName());
        var uri = uriBuilder.path("/api/access-requests/{id}").buildAndExpand(newRequest.id()).toUri();

        return ResponseEntity.created(uri).body(newRequest);
    }

    @GetMapping
    public ResponseEntity<Page<AccessRequestResponseDTO>> getAll(@RequestParam(required = false) AccessStatus status, @PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getAll(status, pageable));
    }

    @GetMapping("/mine")
    public ResponseEntity<Page<AccessRequestResponseDTO>> getMine(Authentication authentication, @PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getMine(authentication.getName(), pageable));
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<AccessRequestResponseDTO>> getPending(@PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getPending(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessRequestResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApprovedAccessResponseDTO> approve(@PathVariable UUID id, Authentication authentication) {

        return ResponseEntity.ok(service.approve(id, authentication.getName()));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<AccessRequestResponseDTO> reject(@PathVariable UUID id, @RequestBody @Valid RejectAccessRequestDTO request, Authentication authentication) {

        return ResponseEntity.ok(service.reject(id, request, authentication.getName()));
    }
}
