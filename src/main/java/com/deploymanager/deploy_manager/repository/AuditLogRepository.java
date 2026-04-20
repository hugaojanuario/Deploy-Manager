package com.deploymanager.deploy_manager.repository;

import com.deploymanager.deploy_manager.entity.auditLog.AuditLog;
import com.deploymanager.deploy_manager.entity.auditLog.enums.AuditAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    @Query("""                                                                                                                                           
              SELECT a FROM AuditLog a                                                                                                                   
              WHERE (:userId IS NULL OR a.actor.id = :userId)
              AND (:clientId IS NULL OR a.client.id = :clientId)
              AND (:action IS NULL OR a.action = :action)
              AND (:dateFrom IS NULL OR a.createdAt >= :dateFrom)
              AND (:dateTo IS NULL OR a.createdAt <= :dateTo)                                                                                              
              """)
    Page<AuditLog> findWithFilters(
            @Param("userId") UUID userId,
            @Param("clientId") UUID clientId,
            @Param("action") AuditAction action,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            Pageable pageable
    );
}
