package com.deploymanager.deploy_manager.entity.user.dtos.user;

import com.deploymanager.deploy_manager.entity.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDTO(
        @NotBlank String name,

        @NotNull UserRole role) {
}
