package com.deploymanager.deploy_manager.domain.kafka.events;

import com.deploymanager.deploy_manager.domain.accessRequest.enums.AccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessResponseEvent {

    private UUID requestId;
    private UUID requesterId;
    private UUID clientId;
    private AccessStatus status;
    private LocalDateTime respondedAt;
    private LocalDateTime expiresAt;
}
