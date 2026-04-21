package com.deploymanager.deploy_manager.entity.user.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordUserRequestDTO(
        @NotBlank String password) {
}
