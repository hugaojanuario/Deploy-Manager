package com.deploymanager.deploy_manager.domain.kafka;

import com.deploymanager.deploy_manager.domain.kafka.events.AccessRequestEvent;
import com.deploymanager.deploy_manager.domain.kafka.events.AccessResponseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishAccessRequestEvent(AccessRequestEvent event) {
        kafkaTemplate.send("access-requests", event);
    }

    public void publishAccessResponseEvent(AccessResponseEvent event) {
        kafkaTemplate.send("access-responses", event);
    }
}
