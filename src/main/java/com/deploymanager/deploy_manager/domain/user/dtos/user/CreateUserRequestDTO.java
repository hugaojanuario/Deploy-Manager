package com.deploymanager.deploy_manager.domain.user.dtos.user;

import com.deploymanager.deploy_manager.domain.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(
        @NotBlank String name,

        @NotBlank @Email String email,

        @NotBlank String password,

        @NotNull UserRole role) {
}
