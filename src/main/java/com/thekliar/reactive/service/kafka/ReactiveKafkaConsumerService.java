package com.thekliar.reactive.service.kafka;

import com.thekliar.reactive.model.event.BaseMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveKafkaConsumerService implements CommandLineRunner {

  private final ReactiveKafkaConsumerTemplate<String, BaseMessage> reactiveKafkaConsumerTemplate;

  @SuppressWarnings("unused")
  private Flux<BaseMessage> consumeBaseMessage() {
    return reactiveKafkaConsumerTemplate
        .receiveAutoAck()
        .doOnNext(consumerRecord ->
            log.info("RECEIVED key={}, value={} from topic={}, offset={}",
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.offset())
        )
        .map(ConsumerRecord::value)
        .doOnNext(dto ->
            log.info("Successfully consumed {}={}", BaseMessage.class.getSimpleName(), dto))
        .doOnError(throwable ->
            log.error("Something bad happened while consuming : {}", throwable.getMessage()));
  }

  @Override
  public void run(String... args) {
    consumeBaseMessage().subscribe();
  }
}
