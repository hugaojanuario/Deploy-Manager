package com.deploymanager.deploy_manager.entity.auditLog.dtos;

import com.deploymanager.deploy_manager.entity.auditLog.enums.AuditAction;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAuditLogRequestDTO(
        @NotNull UUID actor,
        @NotNull AuditAction action,
        UUID client,
        UUID requester,
        String detail,
        String ipAddress
        ) {
}
