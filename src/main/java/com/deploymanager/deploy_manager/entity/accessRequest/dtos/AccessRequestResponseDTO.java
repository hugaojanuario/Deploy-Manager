package com.deploymanager.deploy_manager.entity.accessRequest.dtos;

import com.deploymanager.deploy_manager.entity.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.entity.accessRequest.enums.AccessStatus;

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
    public AccessRequestResponseDTO(AccessRequest accessRequest) {
        this(
                accessRequest.getId(),
                accessRequest.getClient().getId(),
                accessRequest.getRequester().getId(),
                accessRequest.getApprover() != null ? accessRequest.getApprover().getId() : null,
                accessRequest.getStatus(),
                accessRequest.getReason(),
                accessRequest.getRequestedAt(),
                accessRequest.getRespondedAt(),
                accessRequest.getExpiresAt()
        );
    }
}
