package com.deploymanager.deploy_manager.service;

import com.deploymanager.deploy_manager.entity.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.entity.auditLog.AuditLog;
import com.deploymanager.deploy_manager.entity.auditLog.dtos.AuditLogResponseDTO;
import com.deploymanager.deploy_manager.entity.auditLog.dtos.CreateAuditLogRequestDTO;
import com.deploymanager.deploy_manager.entity.auditLog.enums.AuditAction;
import com.deploymanager.deploy_manager.entity.client.Client;
import com.deploymanager.deploy_manager.entity.user.User;
import com.deploymanager.deploy_manager.repository.AccessRequestRepository;
import com.deploymanager.deploy_manager.repository.AuditLogRepository;
import com.deploymanager.deploy_manager.repository.ClientRepository;
import com.deploymanager.deploy_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AccessRequestRepository accessRequestRepository;

    public void log(CreateAuditLogRequestDTO request){
        User actor = userRepository.findById(request.actor())
                .orElseThrow(() -> new RuntimeException());

        Client client = request.client() != null
                ? clientRepository.findById(request.client()).orElse(null)
                : null;

        AccessRequest access = request.requester() != null
                ? accessRequestRepository.findById(request.requester()).orElse(null)
                : null;

        AuditLog auditLog = new AuditLog(
                null,
                actor,
                request.action(),
                client,
                access,
                request.detail(),
                request.ipAddress(),
                null
        );

        auditLogRepository.save(auditLog);
    }

    public Page<AuditLogResponseDTO> getAll(UUID userId, UUID clientId, AuditAction action, LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable){

        return auditLogRepository.findWithFilters(userId, clientId, action, dateFrom, dateTo, pageable).map(AuditLogResponseDTO::new);
    }

    public AuditLogResponseDTO getById (UUID id){
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new AuditLogResponseDTO(log);
    }


}
