package com.toyoda.fipe.api.infrastructure.messaging.kafka.producer;

import com.toyoda.fipe.api.application.port.output.messaging.MessageProducerPort;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class BrandMessageProducer implements MessageProducerPort {

    private static final Logger logger = LoggerFactory.getLogger(BrandMessageProducer.class);

    @Value("${kafka.topic.brand-events}")
    private String topic;

    private final KafkaTemplate<String, Object> jsonKafkaTemplate;

    public BrandMessageProducer(KafkaTemplate<String, Object> jsonKafkaTemplate) {
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    @Override
    public void sendMessage(final Object object) {
        logger.info("Sent message to topic: {} - Objeto: {}", topic, object.getClass().getSimpleName());

        CompletableFuture<SendResult<String, Object>> future = jsonKafkaTemplate.send(topic, object);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Message JSON sent with success to topic: {} - Offset: {}",
                        topic, result.getRecordMetadata().offset());
            } else {
                logger.error("Error while send message to topic: {}", topic, ex);
            }
        });
    }
}
