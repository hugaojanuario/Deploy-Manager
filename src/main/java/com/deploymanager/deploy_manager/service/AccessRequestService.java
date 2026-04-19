package com.deploymanager.deploy_manager.service;

import com.deploymanager.deploy_manager.entity.accessRequest.AccessRequest;
import com.deploymanager.deploy_manager.entity.accessRequest.dtos.AccessRequestResponseDTO;
import com.deploymanager.deploy_manager.entity.accessRequest.dtos.ApprovedAccessResponseDTO;
import com.deploymanager.deploy_manager.entity.accessRequest.dtos.CreateAccessRequestDTO;
import com.deploymanager.deploy_manager.entity.accessRequest.dtos.RejectAccessRequestDTO;
import com.deploymanager.deploy_manager.entity.accessRequest.enums.AccessStatus;
import com.deploymanager.deploy_manager.entity.client.Client;
import com.deploymanager.deploy_manager.entity.user.User;
import com.deploymanager.deploy_manager.repository.AccessRequestRepository;
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
public class AccessRequestService {

    private final AccessRequestRepository repository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public AccessRequestResponseDTO create(CreateAccessRequestDTO request, String requesterEmail) {
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new RuntimeException());

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new RuntimeException());

        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setRequester(requester);
        accessRequest.setClient(client);
        accessRequest.setReason(request.reason());
        accessRequest.setStatus(AccessStatus.PENDING);

        AccessRequest saved = repository.save(accessRequest);

        return new AccessRequestResponseDTO(saved);
    }

    public Page<AccessRequestResponseDTO> getAll(AccessStatus status, Pageable pageable) {
        if (status != null) {
            return repository.findByStatus(status, pageable).map(AccessRequestResponseDTO::new);
        }
        return repository.findAll(pageable).map(AccessRequestResponseDTO::new);
    }

    public Page<AccessRequestResponseDTO> getMine(String requesterEmail, Pageable pageable) {
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new RuntimeException());

        return repository.findByRequester_Id(requester.getId(), pageable).map(AccessRequestResponseDTO::new);
    }

    public Page<AccessRequestResponseDTO> getPending(Pageable pageable) {
        return repository.findByStatus(AccessStatus.PENDING, pageable).map(AccessRequestResponseDTO::new);
    }

    public AccessRequestResponseDTO getById(UUID id) {
        AccessRequest accessRequest = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new AccessRequestResponseDTO(accessRequest);
    }

    public ApprovedAccessResponseDTO approve(UUID id, String approverEmail) {
        AccessRequest accessRequest = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        User approver = userRepository.findByEmail(approverEmail)
                .orElseThrow(() -> new RuntimeException());

        accessRequest.setApprover(approver);
        accessRequest.setStatus(AccessStatus.APPROVED);
        accessRequest.setRespondedAt(LocalDateTime.now());
        accessRequest.setExpiresAt(LocalDateTime.now().plusHours(8));

        AccessRequest saved = repository.save(accessRequest);

        return new ApprovedAccessResponseDTO(saved);
    }

    public AccessRequestResponseDTO reject(UUID id, RejectAccessRequestDTO request, String approverEmail) {
        AccessRequest accessRequest = repository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        User approver = userRepository.findByEmail(approverEmail)
                .orElseThrow(() -> new RuntimeException());

        accessRequest.setApprover(approver);
        accessRequest.setStatus(AccessStatus.REJECTED);
        accessRequest.setRespondedAt(LocalDateTime.now());

        AccessRequest saved = repository.save(accessRequest);

        return new AccessRequestResponseDTO(saved);
    }
}
