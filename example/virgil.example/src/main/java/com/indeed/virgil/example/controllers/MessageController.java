package com.indeed.virgil.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.indeed.virgil.example.models.CustomMessage;
import com.indeed.virgil.example.models.GenerateMessagePayload;
import com.indeed.virgil.example.utils.MessagePublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@Component
public class MessageController {

    private final MessagePublisher messagePublisher;

    public MessageController(
        final MessagePublisher messagePublisher
    ) {
        this.messagePublisher = messagePublisher;
    }

    @PostMapping("/message")
    public boolean create(
        @RequestBody final GenerateMessagePayload payload
    ) throws JsonProcessingException {
        return switch(payload.getSendToDlq()) {
            case true -> {
                for (int i = 0; i < payload.getNum(); i++) {
                    messagePublisher.sendMessageToDlq(generateMessage());
                }
                yield true;
            }
            case false -> {
                for (int i = 0; i < payload.getNum(); i++) {
                    messagePublisher.sendMessageToQueue(generateMessage());
                }
                yield true;
            }
        };
    }

    private CustomMessage generateMessage() {
        return new CustomMessage(new Random().nextLong(), UUID.randomUUID().toString());
    }
}
