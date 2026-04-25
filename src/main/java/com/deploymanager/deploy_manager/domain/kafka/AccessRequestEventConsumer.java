package com.deploymanager.deploy_manager.domain.kafka;

import com.deploymanager.deploy_manager.domain.kafka.events.AccessRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessRequestEventConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "access-requests", groupId = "deploy-manager-group")
    public void consume (AccessRequestEvent event){
        simpMessagingTemplate.convertAndSend("/topic/access-requests", event);
    }
}
