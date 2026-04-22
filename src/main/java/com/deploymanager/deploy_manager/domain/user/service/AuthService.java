package com.deploymanager.deploy_manager.domain.user.service;

import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.user.dtos.auth.AuthRegisterResponseDTO;
import com.deploymanager.deploy_manager.domain.user.dtos.user.CreateUserRequestDTO;
import com.deploymanager.deploy_manager.domain.user.enums.UserRole;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException());
    }

    public AuthRegisterResponseDTO register (@Valid CreateUserRequestDTO request){

        if (userRepository.findByEmail(request.email()).isPresent()){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Email already registred");
        }
        String encryptPassword = passwordEncoder.encode(request.password());
        User newUser = new User(request.email(), encryptPassword, UserRole.USER);
        newUser.setName(request.name());
        newUser.setActive(true);
        this.userRepository.save(newUser);

        return new AuthRegisterResponseDTO(newUser);
    }
}
