package com.deploymanager.deploy_manager.entity.client.dtos;

import com.deploymanager.deploy_manager.entity.client.Client;
import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientPublicResponse(
        UUID id,
        String name,
        String anydeskId,
        String teamviewerId,
        String anyviewerId,
        TypeServerClient server,
        String notes,
        LocalDateTime createdAt
) {
    public ClientPublicResponse(Client client){
        this(
                client.getId(),
                client.getName(),
                client.getAnydeskId(),
                client.getTeamviewerId(),
                client.getAnyviewerId(),
                client.getServer(),
                client.getNotes(),
                client.getCreatedAt()
        );
    }
}
