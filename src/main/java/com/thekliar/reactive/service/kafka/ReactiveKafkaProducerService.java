package com.thekliar.reactive.service.kafka;

import java.time.Instant;
import com.thekliar.reactive.model.event.BaseMessage;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveKafkaProducerService {

  private final ReactiveKafkaProducerTemplate<String, BaseMessage> kafkaTemplate;

  public <T extends BaseMessage> void sendMessage(String topicName, T message) {
    message.setTimestamp(Instant.now());
    log.info("SENDING to topic={}, {}={},", topicName, message.getClass().getSimpleName(), message);
    kafkaTemplate.send(topicName, message)
        .doOnSuccess(senderResult ->
            log.info("Sent {} offset : {}", message, senderResult.recordMetadata().offset()))
        .subscribe();
  }
}
