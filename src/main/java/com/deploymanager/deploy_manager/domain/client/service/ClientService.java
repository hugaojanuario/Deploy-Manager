package com.deploymanager.deploy_manager.domain.client.service;

import com.deploymanager.deploy_manager.domain.client.Client;
import com.deploymanager.deploy_manager.domain.client.dtos.ClientPrivateResponseDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.ClientPublicResponseDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.CreateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.client.dtos.UpdateClientRequestDTO;
import com.deploymanager.deploy_manager.domain.user.User;
import com.deploymanager.deploy_manager.domain.client.repository.ClientRepository;
import com.deploymanager.deploy_manager.domain.user.repository.UserRepository;
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

    public ClientPrivateResponseDTO create (CreateClientRequestDTO request){
        User user = userRepository.findById(request.createdBy())
                .orElseThrow(() -> new RuntimeException());

        Client client = new Client();
        client.setCreatedBy(user);
        client.setName(request.name());
        client.setAnydeskId(request.anydeskId());
        client.setAnydeskPassword(request.anydeskPassword());
        client.setTeamviewerId(request.teamviewerId());
        client.setTeamviewerPassword(request.teamviewerPassword());
        client.setAnyviewerId(request.anyviewerId());
        client.setAnyviewerPassword(request.anyviewerPassword());
        client.setServer(request.server());
        client.setServerUsername(request.serverUsername());
        client.setServerPassword(request.serverPassword());
        client.setDbUser(request.dbUser());
        client.setDbPassword(request.dbPassword());
        client.setNotes(request.notes());
        client.setSentinelUrl(request.sentinelUrl());
        client.setSentinelToken(request.sentinelToken());
        client.setActive(true);

        Client saved = clientRepository.save(client);

        return new ClientPrivateResponseDTO(saved);
    }

    public Page<ClientPrivateResponseDTO> getAllForAdmin (Pageable pageable){
        return clientRepository.findByActiveTrue(pageable).map(ClientPrivateResponseDTO:: new);
    }

    public Page<ClientPublicResponseDTO> getAllForUser (Pageable pageable){
        return clientRepository.findByActiveTrue(pageable).map(ClientPublicResponseDTO:: new);
    }

    public ClientPrivateResponseDTO getByIdForAdmin (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new ClientPrivateResponseDTO(client);
    }

    public ClientPublicResponseDTO getByIdForUser (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        return new ClientPublicResponseDTO(client);
    }

    public ClientPrivateResponseDTO update (UUID id, UpdateClientRequestDTO request){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        if (request.name() != null) client.setName(request.name());
        if (request.anydeskId() != null) client.setAnydeskId(request.anydeskId());
        if (request.anydeskPassword() != null) client.setAnydeskPassword(request.anydeskPassword());
        if (request.teamviewerId() != null) client.setTeamviewerId(request.teamviewerId());
        if (request.teamviewerPassword() != null) client.setTeamviewerPassword(request.teamviewerPassword());
        if (request.anyviewerId() != null) client.setAnyviewerId(request.anyviewerId());
        if (request.anyviewerPassword() != null) client.setAnyviewerPassword(request.anyviewerPassword());
        if (request.server() != null) client.setServer(request.server());
        if (request.serverUsername() != null) client.setServerUsername(request.serverUsername());
        if (request.serverPassword() != null) client.setServerPassword(request.serverPassword());
        if (request.dbUser() != null) client.setDbUser(request.dbUser());
        if (request.dbPassword() != null) client.setDbPassword(request.dbPassword());
        if (request.notes() != null) client.setNotes(request.notes());
        if (request.sentinelUrl() != null) client.setSentinelUrl(request.sentinelUrl());
        if (request.sentinelToken() != null) client.setSentinelToken(request.sentinelToken());

        Client updated = clientRepository.save(client);

        return new ClientPrivateResponseDTO(updated);
    }

    public void softDelete (UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        client.setActive(false);

        clientRepository.save(client);
    }

}
