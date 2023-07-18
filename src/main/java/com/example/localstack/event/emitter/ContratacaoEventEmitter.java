package com.example.localstack.event.emitter;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Component
public class ContratacaoEventEmitter implements EventEmitter<ContratacaoRequest> {

    private final SqsClient sqsClient;

    public ContratacaoEventEmitter(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void emit(ContratacaoRequest message) {
        String json = toJson(message);

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .messageBody(json)
                .build());
    }

    private String toJson(ContratacaoRequest message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
