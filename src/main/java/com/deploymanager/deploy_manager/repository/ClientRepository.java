package com.deploymanager.deploy_manager.repository;

import com.deploymanager.deploy_manager.entity.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Page <Client> findByActiveTrue(Pageable pageable);
}
