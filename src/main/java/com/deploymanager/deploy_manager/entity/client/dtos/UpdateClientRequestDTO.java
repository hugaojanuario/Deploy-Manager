package com.deploymanager.deploy_manager.entity.client.dtos;

import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;

public record UpdateClientRequestDTO(
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
        String sentinelToken
) {
}
