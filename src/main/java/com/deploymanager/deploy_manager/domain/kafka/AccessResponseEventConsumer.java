package com.deploymanager.deploy_manager.domain.kafka;

import com.deploymanager.deploy_manager.domain.kafka.events.AccessResponseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessResponseEventConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "access-responses", groupId = "deploy-manager-group")
    public void consume(AccessResponseEvent event) {
        simpMessagingTemplate.convertAndSend("/topic/access-approved/" + event.getRequesterId(), event);
    }
}
