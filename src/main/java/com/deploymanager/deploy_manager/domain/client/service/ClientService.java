package com.deploymanager.deploy_manager.domain.client.service;

import com.deploymanager.deploy_manager.domain.auditLog.dtos.CreateAuditLogRequestDTO;
import com.deploymanager.deploy_manager.domain.auditLog.enums.AuditAction;
import com.deploymanager.deploy_manager.domain.auditLog.service.AuditLogService;
import com.deploymanager.deploy_manager.domain.client.Client;
import com.deploymanager.deploy_manager.domain.client.dtos.ClientPrivateResponseDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.ClientPublicResponseDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.CreateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.UpdateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.client.repository.ClientRepository;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
import com.deploymanager.deploy_manager.infra.crypto.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final CryptoService cryptoService;

    public ClientPrivateResponseDTO create (CreateClientRequestDTO request){
        User user = userRepository.findById(request.createdBy())
                .orElseThrow(() -> new RuntimeException());

        Client client = new Client();
        client.setCreatedBy(user);
        client.setName(request.name());
        client.setAnydeskId(request.anydeskId());
        client.setAnydeskPassword(cryptoService.encrypt(request.anydeskPassword()));
        client.setTeamviewerId(request.teamviewerId());
        client.setTeamviewerPassword(cryptoService.encrypt(request.teamviewerPassword()));
        client.setAnyviewerId(request.anyviewerId());
        client.setAnyviewerPassword(cryptoService.encrypt(request.anyviewerPassword()));
        client.setServer(request.server());
        client.setServerUsername(request.serverUsername());
        client.setServerPassword(cryptoService.encrypt(request.serverPassword()));
        client.setDbUser(request.dbUser());
        client.setDbPassword(cryptoService.encrypt(request.dbPassword()));
        client.setNotes(request.notes());
        client.setSentinelUrl(request.sentinelUrl());
        client.setSentinelToken(cryptoService.encrypt(request.sentinelToken()));
        client.setActive(true);

        Client saved = clientRepository.save(client);

        auditLogService.log(new CreateAuditLogRequestDTO(
                user.getId(),
                AuditAction.CLIENT_CREATED,
                saved.getId(),
                null,
                null,
                null
        ));

        decryptFields(saved);

        return new ClientPrivateResponseDTO(saved);
    }

    public Page<ClientPrivateResponseDTO> getAllForAdmin (Pageable pageable){
        return clientRepository.findByActiveTrue(pageable).map(client -> {
            decryptFields(client);
            return new ClientPrivateResponseDTO(client);
        });
    }

    public Page<ClientPublicResponseDTO> getAllForUser (Pageable pageable){
        return clientRepository.findByActiveTrue(pageable).map(ClientPublicResponseDTO:: new);
    }

    public ClientPrivateResponseDTO getByIdForAdmin (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        decryptFields(client);

        return new ClientPrivateResponseDTO(client);
    }

    public ClientPublicResponseDTO getByIdForUser (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new ClientPublicResponseDTO(client);
    }

    public ClientPrivateResponseDTO update (UUID id, UpdateClientRequestDTO request, String actorEmail){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() -> new RuntimeException());

        if (request.name() != null) client.setName(request.name());
        if (request.anydeskId() != null) client.setAnydeskId(request.anydeskId());
        if (request.anydeskPassword() != null) client.setAnydeskPassword(cryptoService.encrypt(request.anydeskPassword()));
        if (request.teamviewerId() != null) client.setTeamviewerId(request.teamviewerId());
        if (request.teamviewerPassword() != null) client.setTeamviewerPassword(cryptoService.encrypt(request.teamviewerPassword()));
        if (request.anyviewerId() != null) client.setAnyviewerId(request.anyviewerId());
        if (request.anyviewerPassword() != null) client.setAnyviewerPassword(cryptoService.encrypt(request.anyviewerPassword()));
        if (request.server() != null) client.setServer(request.server());
        if (request.serverUsername() != null) client.setServerUsername(request.serverUsername());
        if (request.serverPassword() != null) client.setServerPassword(cryptoService.encrypt(request.serverPassword()));
        if (request.dbUser() != null) client.setDbUser(request.dbUser());
        if (request.dbPassword() != null) client.setDbPassword(cryptoService.encrypt(request.dbPassword()));
        if (request.notes() != null) client.setNotes(request.notes());
        if (request.sentinelUrl() != null) client.setSentinelUrl(request.sentinelUrl());
        if (request.sentinelToken() != null) client.setSentinelToken(cryptoService.encrypt(request.sentinelToken()));

        Client updated = clientRepository.save(client);

        auditLogService.log(new CreateAuditLogRequestDTO(
                actor.getId(),
                AuditAction.CLIENT_UPDATED,
                updated.getId(),
                null,
                null,
                null
        ));

        decryptFields(updated);

        return new ClientPrivateResponseDTO(updated);
    }

    public void softDelete (UUID id, String actorEmail){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() -> new RuntimeException());

        client.setActive(false);

        clientRepository.save(client);

        auditLogService.log(new CreateAuditLogRequestDTO(
                actor.getId(),
                AuditAction.CLIENT_UPDATED,
                client.getId(),
                null,
                null,
                null
        ));
    }

    private void decryptFields(Client client) {
        client.setAnydeskPassword(cryptoService.decrypt(client.getAnydeskPassword()));
        client.setTeamviewerPassword(cryptoService.decrypt(client.getTeamviewerPassword()));
        client.setAnyviewerPassword(cryptoService.decrypt(client.getAnyviewerPassword()));
        client.setServerPassword(cryptoService.decrypt(client.getServerPassword()));
        client.setDbPassword(cryptoService.decrypt(client.getDbPassword()));
        client.setSentinelToken(cryptoService.decrypt(client.getSentinelToken()));
    }

}
