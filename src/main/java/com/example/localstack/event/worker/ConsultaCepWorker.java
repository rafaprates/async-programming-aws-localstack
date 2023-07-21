package com.example.localstack.event.worker;

import com.example.localstack.data.repository.CEPRepository;
import com.example.localstack.event.dto.ContratacaoMessage;
import com.example.localstack.event.dto.SnsTopicMessage;
import com.example.localstack.service.ViaCepService;
import com.example.localstack.service.dto.ViaCepResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ConsultaCepWorker implements Worker<ContratacaoMessage> {

    private final SqsClient sqsClient;
    private final ViaCepService viaCepService;
    private final CEPRepository cepRepository;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void listen() {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/consulta-cep-queue")
                .build();
        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            log.info("Mensagem recebida por CEPWorker: {}", message.body());
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
        log.info("Processar CEP {}", message.cep());

        ViaCepResponse response = viaCepService.consultar(message.cep());

       cepRepository.findById(message.cep()).ifPresent(
                cep -> {
                    cep.update(
                            response.logradouro(),
                            response.complemento(),
                            response.bairro(),
                            response.localidade(),
                            response.uf(),
                            response.ibge(),
                            response.gia(),
                            response.ddd(),
                            response.siafi()
                    );
                    cepRepository.save(cep);
                }
       );
    }

    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/consulta-cep-queue")
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }
}
