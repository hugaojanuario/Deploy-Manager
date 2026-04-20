package com.deploymanager.deploy_manager.entity.auditLog.dtos;

import com.deploymanager.deploy_manager.entity.auditLog.enums.AuditAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAuditLogDTO(
        @NotNull UUID actor,
        @NotNull AuditAction action,
        UUID client,
        UUID request,
        String detail,
        String ipAddress
        ) {
}
