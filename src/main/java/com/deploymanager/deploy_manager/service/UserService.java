package com.deploymanager.deploy_manager.service;

import com.deploymanager.deploy_manager.entity.user.User;
import com.deploymanager.deploy_manager.entity.user.dtos.CreateUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UpdatePasswordUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UpdateUserRequest;
import com.deploymanager.deploy_manager.entity.user.dtos.UserResponse;
import com.deploymanager.deploy_manager.repository.UserRepository;
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

    public UserResponse createUser (CreateUserRequest request){
        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setActive(true);

        User saved = repository.save(user);
        return new UserResponse(saved);
    }

    public Page<UserResponse> getAll (Pageable pageable){
        return repository.findByActiveTrue(pageable).map(UserResponse :: new);
    }

    public UserResponse getById(UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new UserResponse(user);
    }

    public UserResponse update (UUID id, UpdateUserRequest request){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if(request.name() != null){
            user.setName(request.name());
        }
        if(request.role() != null){
            user.setRole(request.role());
        }

        User updated = repository.save(user);

        return new UserResponse(updated);
    }

    public UserResponse updatePassword (UUID id, UpdatePasswordUserRequest request){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if(request.password() != null){
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        User updated = repository.save(user);

        return new UserResponse(updated);
    }

    public void softDelete (UUID id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        user.setActive(false);
        repository.save(user);
    }

}

