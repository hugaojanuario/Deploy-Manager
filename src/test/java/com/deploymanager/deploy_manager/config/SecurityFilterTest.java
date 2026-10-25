package com.deploymanager.deploy_manager.config;

import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.user.enums.UserRole;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private SecurityFilter securityFilter;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldNotAuthenticateDisabledUser() throws Exception {
        // Arrange
        User user = new User("rita@email.com", "hash", UserRole.USER);
        user.setActive(false);

        when(tokenService.validateToken("valid-token")).thenReturn("rita@email.com");
        when(userRepository.findByEmail("rita@email.com")).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldAuthenticateActiveUser() throws Exception {
        // Arrange
        User user = new User("rita@email.com", "hash", UserRole.USER);
        user.setActive(true);

        when(tokenService.validateToken("valid-token")).thenReturn("rita@email.com");
        when(userRepository.findByEmail("rita@email.com")).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        verify(filterChain).doFilter(request, response);
    }

}
