package com.deploymanager.deploy_manager.domain.user.dtos.user;

import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.user.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        UserRole role,
        Boolean active,
        LocalDateTime createdAt) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt()
        );
    }
}
