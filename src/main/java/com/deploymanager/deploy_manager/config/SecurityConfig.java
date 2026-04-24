package com.deploymanager.deploy_manager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        //WebSocket
                        .requestMatchers("/ws/**").permitAll()

                        //User
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users").hasRole("ADMIN")

                        //Client
                        .requestMatchers(HttpMethod.POST, "/api/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/clients").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/clients/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/clients/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clients/{id}").hasRole("ADMIN")

                        //AuditLog
                        .requestMatchers(HttpMethod.GET, "/api/audit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/audit/{id}").hasRole("ADMIN")

                        //AccessRequest
                        .requestMatchers(HttpMethod.POST, "/api/access-requests").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/access-requests").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/access-requests/mine").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/access-requests/pending").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/access-requests/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/access-requests/{id}/approve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/access-requests/{id}/reject").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
