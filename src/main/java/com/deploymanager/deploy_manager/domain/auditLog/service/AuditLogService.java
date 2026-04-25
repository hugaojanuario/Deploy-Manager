package com.deploymanager.deploy_manager.domain.auditLog.service;

import com.deploymanager.deploy_manager.domain.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.domain.auditLog.AuditLog;
import com.deploymanager.deploy_manager.domain.auditLog.dtos.AuditLogResponseDTO;
import com.deploymanager.deploy_manager.domain.auditLog.dtos.CreateAuditLogRequestDTO;
import com.deploymanager.deploy_manager.domain.auditLog.enums.AuditAction;
import com.deploymanager.deploy_manager.domain.client.Client;
import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.accessRequest.repository.AccessRequestRepository;
import com.deploymanager.deploy_manager.domain.auditLog.repository.AuditLogRepository;
import com.deploymanager.deploy_manager.domain.client.repository.ClientRepository;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
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

        AuditLog auditLog = AuditLog.builder()
                .actor(actor)
                .action(request.action())
                .client(client)
                .accessRequest(access)
                .detail(request.detail())
                .ipAddress(request.ipAddress())
                .build();

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
