package com.deploymanager.deploy_manager.entity.accessRequest.dtos;

import com.deploymanager.deploy_manager.entity.accessRequest.enums.AccessStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccessRequestResponseDTO(
        UUID id,
        UUID clientId,
        UUID requesterId,
        UUID approverId,
        AccessStatus status,
        String reason,
        LocalDateTime requestedAt,
        LocalDateTime respondedAt,
        LocalDateTime expiresAt
) {
}
