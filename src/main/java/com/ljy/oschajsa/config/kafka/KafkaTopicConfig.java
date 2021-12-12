package com.ljy.oschajsa.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class KafkaTopicConfig {
    @Value("${kafka.topic.store.addedTag}")
    private String ADDED_TAG_TOPIC;

    @Bean
    NewTopic addedTagTopic(){
        return new NewTopic(ADDED_TAG_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.removedTag}")
    private String REMOVED_TAG_TOPIC;

    @Bean
    NewTopic removedTagTopic(){
        return new NewTopic(REMOVED_TAG_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.changedBusinessName}")
    private String CHANGED_BUSINESSNAME_TOPIC;

    @Bean
    NewTopic changedBusinessNameTopic(){
        return new NewTopic(CHANGED_BUSINESSNAME_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.changedBusinessHour}")
    private String CHANGED_BUSINESSHOUR_TOPIC;

    @Bean
    NewTopic changedBusinessHourTopic(){
        return new NewTopic(CHANGED_BUSINESSHOUR_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.changedBusinessTel}")
    private String CHANGED_BUSINESSTEL_TOPIC;

    @Bean
    NewTopic changedBusinessTelTopic(){
        return new NewTopic(CHANGED_BUSINESSTEL_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.changedLogo}")
    private String CHANGED_LOGO_TOPIC;

    @Bean
    NewTopic changedLogoTopic(){
        return new NewTopic(CHANGED_LOGO_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.closedStore}")
    private String CLOSED_STORE_TOPIC;

    @Bean
    NewTopic closedStoreTopic(){
        return new NewTopic(CLOSED_STORE_TOPIC, (short) 1, (short) 1);
    }

    @Value("${kafka.topic.store.opendStore}")
    private String OPENED_STORE_TOPIC;

    @Bean
    NewTopic opendStoreTopic(){
        return new NewTopic(OPENED_STORE_TOPIC, (short) 1, (short) 1);
    }

}