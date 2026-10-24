package com.deploymanager.deploy_manager.domain.user.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterRequestDTO(
        @NotBlank String name,

        @NotBlank @Email String email,

        @NotBlank String password
) {
}
