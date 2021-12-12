package com.ljy.oschajsa.services.store.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljy.oschajsa.services.store.domain.event.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StoreEventProducer {
    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private ObjectMapper objectMapper;

    @Value("${kafka.topic.store.addedTag}")
    private String ADDED_TAG_TOPIC;

    @EventListener
    void handle(AddedTagEvent event) throws Exception {
        kafkaTemplate.send(ADDED_TAG_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.removedTag}")
    private String REMOVED_TAG_TOPIC;

    @EventListener
    void handle(RemovedTagEvent event) throws Exception {
        kafkaTemplate.send(REMOVED_TAG_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.changedBusinessName}")
    private String CHANGED_BUSINESSNAME_TOPIC;

    @EventListener
    void handle(ChangedBusinessNameEvent event) throws Exception {
        kafkaTemplate.send(CHANGED_BUSINESSNAME_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.changedBusinessHour}")
    private String CHANGED_BUSINESSHOUR_TOPIC;

    @EventListener
    void handle(ChangedBusinessHourEvent event) throws Exception {
        kafkaTemplate.send(CHANGED_BUSINESSHOUR_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.changedBusinessTel}")
    private String CHANGED_BUSINESSTEL_TOPIC;

    @EventListener
    void handle(ChangedBusinessTelEvent event) throws Exception {
        kafkaTemplate.send(CHANGED_BUSINESSTEL_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.changedLogo}")
    private String CHANGED_LOGO_TOPIC;

    @EventListener
    void handle(ChangedLogoEvent event) throws Exception {
        kafkaTemplate.send(CHANGED_LOGO_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.closedStore}")
    private String CLOSED_STORE_TOPIC;

    @EventListener
    void handle(ClosedStoreEvent event) throws Exception {
        kafkaTemplate.send(CLOSED_STORE_TOPIC, objectMapper.writeValueAsString(event));
    }

    @Value("${kafka.topic.store.opendStore}")
    private String OPENED_STORE_TOPIC;

    @EventListener
    void handle(OpenedStoreEvent event) throws Exception {
        kafkaTemplate.send(OPENED_STORE_TOPIC, objectMapper.writeValueAsString(event));
    }
}
