package com.example.localstack.event.worker;

import com.example.localstack.data.repository.CPFRepository;
import com.example.localstack.data.schema.CPF;
import com.example.localstack.event.dto.ContratacaoMessage;
import com.example.localstack.event.dto.SnsTopicMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsultaCpfWorker implements Worker<ContratacaoMessage> {

    @Value("${env.aws.sqs.queue.consulta-cpf-queue}")
    private String queueUrl;
    private final SqsClient sqsClient;
    private final CPFRepository cpfRepository;
    private final MeterRegistry meter;

    @Scheduled(fixedDelay = 5000)
    public void listen() {
        meter.counter("workers.cpf", "job", "listen").increment();
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .build();
        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            log.info("[CPF Worker] [Mensagem: {}] Recebida", message.body());
            message.getValueForField("Body", String.class)
                    .ifPresent(body -> {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            SnsTopicMessage m = mapper.readValue(body, SnsTopicMessage.class);
                            ContratacaoMessage contratacaoMessage = mapper.readValue(m.Message(), ContratacaoMessage.class);
                            process(contratacaoMessage);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });

            deleteMessage(message.receiptHandle());
        }
    }

    @Override
    public void process(ContratacaoMessage message) {
        meter.counter("workers.cpf", "job", "process").increment();
        log.info("[CPF: {}] Processando", message.cpf());
        cpfRepository.save(new CPF(message.cpf()));
    }

    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }
}
