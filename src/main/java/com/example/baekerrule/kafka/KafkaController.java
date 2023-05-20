package com.example.baekerrule.kafka;

import com.example.baekerrule.domain.dto.RuleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaController {
    private final KafkaProducer producer;

    @Autowired
    private KafkaTemplate<String , Object> template;

    @Autowired
    KafkaController(KafkaProducer producer){
        this.producer = producer;
    }


    @PostMapping("/send")
    public String sendMessage(@RequestBody RuleDto ruleDto) {
        log.info("message : {}", ruleDto.toString());
        this.producer.sendMessage(ruleDto);
        return "success";
    }
}
