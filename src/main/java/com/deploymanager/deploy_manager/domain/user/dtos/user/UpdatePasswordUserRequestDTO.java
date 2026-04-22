package com.deploymanager.deploy_manager.domain.user.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordUserRequestDTO(
        @NotBlank String password) {
}
