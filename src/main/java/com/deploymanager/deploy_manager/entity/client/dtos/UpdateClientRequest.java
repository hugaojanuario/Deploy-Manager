package com.deploymanager.deploy_manager.entity.client.dtos;

import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateClientRequest(
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
