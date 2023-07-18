package com.example.localstack.event.listener;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.service.ContratacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Slf4j
@Component
public class ContratacaoEventListener {

    private final SqsClient sqsClient;
    private final ContratacaoService contratacaoService;

    public ContratacaoEventListener(SqsClient sqsClient, ContratacaoService contratacaoService) {
        this.sqsClient = sqsClient;
        this.contratacaoService = contratacaoService;
    }

    @Scheduled(fixedDelay = 1000)
    public void listen() {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .build();
        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            message.getValueForField("Body", String.class)
                    .ifPresent(body -> {
                        log.info("Mensagem recebida: {}", body);
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            ContratacaoRequest contratacaoRequest = mapper.readValue(body, ContratacaoRequest.class);
                            contratacaoService.processar(contratacaoRequest);

                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });

            deleteMessage(message.receiptHandle());
        }
    }

    private void deleteMessage(String receiptHandle) {
        log.info("Deletando mensagem: {}", receiptHandle);
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/contratacao-queue")
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }
}
