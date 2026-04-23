package com.deploymanager.deploy_manager.domain.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessRequestEvent {

    private UUID requestId;
    private UUID requesterId;
    private UUID clientId;
    private String reason;
    private LocalDateTime requestAt;
}
