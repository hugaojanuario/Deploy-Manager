package com.deploymanager.deploy_manager.repository;

import com.deploymanager.deploy_manager.entity.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.entity.accessRequest.enums.AccessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, UUID> {
    Page<AccessRequest> findByRequester_Id(UUID requesterId, Pageable pageable);
    Page<AccessRequest> findByStatus(AccessStatus status, Pageable pageable);
}
