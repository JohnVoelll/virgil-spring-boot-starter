package com.indeed.virgil.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indeed.virgil.example.config.RabbitMqConfig;
import com.indeed.virgil.example.models.CustomMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public final class MessagePublisher {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public MessagePublisher(final RabbitTemplate rabbitTemplate, final ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToQueue(final CustomMessage customMessage) throws JsonProcessingException {
        final String jsonString = objectMapper.writeValueAsString(customMessage);
        final Message message = new Message(jsonString.getBytes(StandardCharsets.UTF_8), new MessageProperties());
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY, message);
    }

    public void sendMessageToDlq(final CustomMessage customMessage) throws JsonProcessingException {
        final String jsonString = objectMapper.writeValueAsString(customMessage);
        final Message message = new Message(jsonString.getBytes(StandardCharsets.UTF_8), new MessageProperties());
        this.rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE_NAME, RabbitMqConfig.DLQ_ROUTING_KEY, message);
    }
}
