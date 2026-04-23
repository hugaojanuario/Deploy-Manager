package com.deploymanager.deploy_manager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic accessRequests(){
        return TopicBuilder.name("access-requests")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic accessResponses(){
        return TopicBuilder.name("access-responses")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sentinelEvents(){
        return TopicBuilder.name("sentinel-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
