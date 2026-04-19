package com.deploymanager.deploy_manager.entity.accessRequest.dtos;

import com.deploymanager.deploy_manager.entity.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.entity.accessRequest.enums.AccessStatus;
import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApprovedAccessResponseDTO(
        UUID requestId,
        UUID clientId,
        String clientName,
        AccessStatus status,
        LocalDateTime expiresAt,

        String anydeskId,
        String anydeskPassword,

        String teamviewerId,
        String teamviewerPassword,

        String anyviewerId,
        String anyviewerPassword,

        TypeServerClient server,
        String serverUsername,
        String serverPassword,

        String dbUser,
        String dbPassword
) {
    public ApprovedAccessResponseDTO(AccessRequest accessRequest) {
        this(
                accessRequest.getId(),
                accessRequest.getClient().getId(),
                accessRequest.getClient().getName(),
                accessRequest.getStatus(),
                accessRequest.getExpiresAt(),
                accessRequest.getClient().getAnydeskId(),
                accessRequest.getClient().getAnydeskPassword(),
                accessRequest.getClient().getTeamviewerId(),
                accessRequest.getClient().getTeamviewerPassword(),
                accessRequest.getClient().getAnyviewerId(),
                accessRequest.getClient().getAnyviewerPassword(),
                accessRequest.getClient().getServer(),
                accessRequest.getClient().getServerUsername(),
                accessRequest.getClient().getServerPassword(),
                accessRequest.getClient().getDbUser(),
                accessRequest.getClient().getDbPassword()
        );
    }
}
