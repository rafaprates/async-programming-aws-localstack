package com.example.localstack.event.worker;

import com.example.localstack.data.schema.CEP;
import com.example.localstack.data.schema.Cliente;
import com.example.localstack.event.dto.ContratacaoMessage;
import com.example.localstack.event.dto.SnsTopicMessage;
import com.example.localstack.service.ClienteService;
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
    private final ClienteService clienteService;
    private final ViaCepService viaCepService;

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
                            ContratacaoMessage contratacaoMessage = mapper.readValue(m.getMessage(), ContratacaoMessage.class);
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
        log.info("Processar CEP para cliente {}", message.getIdCliente());
        Cliente cliente = clienteService.buscarPorId(message.getIdCliente());

        ViaCepResponse response = viaCepService.consultar(cliente.getCep().getCep());

        CEP cep = CEP.builder()
                .cep(cliente.getCep().getCep())
                .cepFormatado(response.getCep())
                .logradouro(response.getLogradouro())
                .bairro(response.getBairro())
                .localidade(response.getLocalidade())
                .uf(response.getUf())
                .ibge(response.getIbge())
                .gia(response.getGia())
                .ddd(response.getDdd())
                .siafi(response.getSiafi())
                .build();

        cliente.setCep(cep);

        clienteService.salvar(cliente);
    }

    private void deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/consulta-cep-queue")
                .receiptHandle(receiptHandle)
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }
}
