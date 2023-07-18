package com.example.localstack.event.emitter;

import com.example.localstack.controller.request.ContratacaoRequest;
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
        log.info("Emitindo mensagem: {}", message);
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .messageBody("Hello world!")
                .delaySeconds(10)
                .build());
    }
}
