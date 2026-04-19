package com.deploymanager.deploy_manager.entity.accessRequest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccessRequestDTO(
        @NotNull UUID clientId,
        @NotBlank String reason
) {
}
