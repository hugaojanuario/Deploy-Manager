package com.deploymanager.deploy_manager.entity.client.dtos;

import com.deploymanager.deploy_manager.entity.client.enums.TypeServerClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateClientRequest (
        @NotBlank String name,
        String anydeskId,
        String anydeskPassword,
        String teamviewerId,
        String teamviewerPassword,
        String anyviewerId,
        String anyviewerPassword,
        @NotNull TypeServerClient server,
        @NotBlank String serverUsername,
        @NotBlank String serverPassword,
        String dbUser,
        String dbPassword,
        String notes,
        String sentinelUrl,
        String sentinelToken,
        @NotNull UUID createdBy
) {
}
