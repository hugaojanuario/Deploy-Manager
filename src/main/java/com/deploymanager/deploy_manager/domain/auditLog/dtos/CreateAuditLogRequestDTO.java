package com.deploymanager.deploy_manager.domain.auditLog.dtos;

import com.deploymanager.deploy_manager.domain.auditLog.enums.AuditAction;
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
