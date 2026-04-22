package com.deploymanager.deploy_manager.domain.user.service;

import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.user.dtos.user.CreateUserRequestDTO;
import com.deploymanager.deploy_manager.domain.user.dtos.user.UpdatePasswordUserRequestDTO;
import com.deploymanager.deploy_manager.domain.user.dtos.user.UpdateUserRequestDTO;
import com.deploymanager.deploy_manager.domain.user.dtos.user.UserResponseDTO;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser (CreateUserRequestDTO request){
        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setActive(true);

        User saved = repository.save(user);
        return new UserResponseDTO(saved);
    }

    public Page<UserResponseDTO> getAll (Pageable pageable){
        return repository.findByActiveTrue(pageable).map(UserResponseDTO:: new);
    }

    public UserResponseDTO getById(UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new UserResponseDTO(user);
    }

    public UserResponseDTO update (UUID id, UpdateUserRequestDTO request){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if(request.name() != null){
            user.setName(request.name());
        }
        if(request.role() != null){
            user.setRole(request.role());
        }

        User updated = repository.save(user);

        return new UserResponseDTO(updated);
    }

    public UserResponseDTO updatePassword (UUID id, UpdatePasswordUserRequestDTO request){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if(request.password() != null){
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        User updated = repository.save(user);

        return new UserResponseDTO(updated);
    }

    public void softDelete (UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        user.setActive(false);
        repository.save(user);
    }

}

