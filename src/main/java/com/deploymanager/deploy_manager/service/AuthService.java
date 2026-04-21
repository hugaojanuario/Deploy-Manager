package com.deploymanager.deploy_manager.service;

import com.deploymanager.deploy_manager.entity.user.User;
import com.deploymanager.deploy_manager.entity.user.dtos.auth.AuthRegisterResponseDTO;
import com.deploymanager.deploy_manager.entity.user.dtos.user.CreateUserRequestDTO;
import com.deploymanager.deploy_manager.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException());
    }

    public AuthRegisterResponseDTO register (@Valid CreateUserRequestDTO request){
        var email = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException());

        if (email != null){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Email already registred");
        }
        String encryptPassword = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.email(), encryptPassword, request.role());
        newUser.setActive(true);
        this.userRepository.save(newUser);

        return new AuthRegisterResponseDTO(newUser);
    }
}
