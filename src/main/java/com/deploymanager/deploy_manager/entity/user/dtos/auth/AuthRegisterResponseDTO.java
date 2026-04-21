package com.deploymanager.deploy_manager.entity.user.dtos.auth;

import com.deploymanager.deploy_manager.entity.user.User;

public record AuthRegisterResponseDTO(String email) {
    public AuthRegisterResponseDTO(User user){
        this(user.getEmail());
    }

}
