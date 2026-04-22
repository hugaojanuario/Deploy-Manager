package com.deploymanager.deploy_manager.domain.auditLog.controller;

import com.deploymanager.deploy_manager.domain.auditLog.dtos.AuditLogResponseDTO;
import com.deploymanager.deploy_manager.domain.auditLog.enums.AuditAction;
import com.deploymanager.deploy_manager.domain.auditLog.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditLogService service;

    @GetMapping
    public ResponseEntity<Page<AuditLogResponseDTO>> getAll (
            @RequestParam (required = false) UUID userId,
            @RequestParam (required = false) UUID clientId,
            @RequestParam (required = false) AuditAction action,
            @RequestParam (required = false) LocalDateTime dateFrom,
            @RequestParam (required = false) LocalDateTime dateTo,
            @PageableDefault(size = 5) Pageable pageable){

        return ResponseEntity.ok(service.getAll(userId, clientId, action, dateFrom, dateTo, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getById (@PathVariable UUID id){
        var log = service.getById(id);

        return ResponseEntity.ok().body(log);
    }



}
