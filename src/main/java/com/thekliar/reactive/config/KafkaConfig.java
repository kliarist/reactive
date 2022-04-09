package com.thekliar.reactive.config;

import java.util.Collections;
import java.util.Map;
import com.thekliar.reactive.model.event.BaseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaConfig {

  @Bean
  public ReceiverOptions<String, BaseMessage> kafkaReceiverOptions(
      @Value(value = "${kafka.topic.blog-events}") String topic, KafkaProperties kafkaProperties) {
    ReceiverOptions<String, BaseMessage> basicReceiverOptions = ReceiverOptions.create(
        kafkaProperties.buildConsumerProperties());
    return basicReceiverOptions.subscription(Collections.singletonList(topic));
  }

  @Bean
  public ReactiveKafkaConsumerTemplate<String, BaseMessage> reactiveKafkaConsumerTemplate(
      ReceiverOptions<String, BaseMessage> kafkaReceiverOptions) {
    return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
  }

  @Bean
  public ReactiveKafkaProducerTemplate<String, BaseMessage> reactiveKafkaProducerTemplate(
      KafkaProperties properties) {
    Map<String, Object> props = properties.buildProducerProperties();
    return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
  }
}
