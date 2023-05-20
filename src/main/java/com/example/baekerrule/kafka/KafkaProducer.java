package com.example.baekerrule.kafka;

import com.example.baekerrule.domain.dto.RuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KafkaProducer {

    @Value(value = "${message.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RuleDto ruleDto) {
        this.kafkaTemplate.send(topicName, ruleDto);
    }
}

