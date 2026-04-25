package com.deploymanager.deploy_manager.domain.accessRequest.dtos;

import jakarta.validation.constraints.NotBlank;

public record RejectAccessRequestDTO(
        @NotBlank String rejectReason
) {
}
