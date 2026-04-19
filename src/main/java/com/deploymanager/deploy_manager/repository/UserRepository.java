package com.deploymanager.deploy_manager.repository;

import com.deploymanager.deploy_manager.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByActiveTrue(Pageable pageable);
    Optional<User> findByEmail(String email);
}
