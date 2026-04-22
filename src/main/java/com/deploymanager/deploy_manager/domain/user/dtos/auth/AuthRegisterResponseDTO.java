package com.deploymanager.deploy_manager.domain.user.dtos.auth;

import com.deploymanager.deploy_manager.domain.user.User;

public record AuthRegisterResponseDTO(String email) {
    public AuthRegisterResponseDTO(User user){
        this(user.getEmail());
    }

}
