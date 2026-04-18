package com.deploymanager.deploy_manager.entity.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordUserRequest (
        @NotBlank String password) {
}
