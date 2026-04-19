package com.deploymanager.deploy_manager.entity.client.dtos;

import com.deploymanager.deploy_manager.entity.client.Client;
import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientPrivateResponse(
        UUID id,
        String name,
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
        String dbPassword,
        String notes,
        String sentinelUrl,
        String sentinelToken,
        UUID createdBy,
        LocalDateTime createdAt
) {
    public ClientPrivateResponse(Client client){
        this(
                client.getId(),
                client.getName(),
                client.getAnydeskId(),
                client.getAnydeskPassword(),
                client.getTeamviewerId(),
                client.getTeamviewerPassword(),
                client.getAnyviewerId(),
                client.getAnyviewerPassword(),
                client.getServer(),
                client.getServerUsername(),
                client.getServerPassword(),
                client.getDbUser(),
                client.getDbPassword(),
                client.getNotes(),
                client.getSentinelUrl(),
                client.getSentinelToken(),
                client.getCreatedBy().getId(),
                client.getCreatedAt()

        );
    }

}
