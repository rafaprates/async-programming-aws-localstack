package com.example.localstack.emitter;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class ContratacaoEventEmitter implements EventEmitter {

    private final SqsClient sqsClient;

    public ContratacaoEventEmitter(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void emit(String message) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .messageBody("Hello world!")
                .delaySeconds(10)
                .build());
    }
}
