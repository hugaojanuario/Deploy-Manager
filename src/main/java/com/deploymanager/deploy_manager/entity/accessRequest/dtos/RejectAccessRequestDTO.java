package com.deploymanager.deploy_manager.entity.accessRequest.dtos;

import jakarta.validation.constraints.NotBlank;

public record RejectAccessRequestDTO(
        @NotBlank String reason
) {
}
