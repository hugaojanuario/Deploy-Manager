package com.deploymanager.deploy_manager.domain.auditLog.dtos;

import com.deploymanager.deploy_manager.domain.auditLog.AuditLog;
import com.deploymanager.deploy_manager.domain.auditLog.enums.AuditAction;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditLogResponseDTO(
        UUID id,
        UUID actor,
        AuditAction action,
        UUID client,
        UUID requester,
        String detail,
        String ipAddress,
        LocalDateTime createdAt
) {
    public AuditLogResponseDTO (AuditLog log){
        this(
                log.getId(),
                log.getActor()!= null ? log.getActor().getId() : null,
                log.getAction(),
                log.getClient() != null ? log.getClient().getId() : null,
                log.getRequester() != null ? log.getRequester().getId() : null,
                log.getDetail(),
                log.getIpAddress(),
                log.getCreatedAt()
        );
    }
}
