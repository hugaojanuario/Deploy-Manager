package com.deploymanager.deploy_manager.domain.accessRequest.repository;

import com.deploymanager.deploy_manager.domain.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.domain.accessRequest.enums.AccessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, UUID> {
    Page<AccessRequest> findByRequester_Id(UUID requesterId, Pageable pageable);
    Page<AccessRequest> findByStatus(AccessStatus status, Pageable pageable);
}
