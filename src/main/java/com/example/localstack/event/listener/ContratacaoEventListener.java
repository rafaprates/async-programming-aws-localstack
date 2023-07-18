package com.example.localstack.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Slf4j
@Component
public class ContratacaoEventListener {

    private final SqsClient sqsClient;

    public ContratacaoEventListener(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedDelay = 1000)
    public void listen() {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .build();
        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        log.info("Mensagens recebidas: " + messages.size());
    }
}
