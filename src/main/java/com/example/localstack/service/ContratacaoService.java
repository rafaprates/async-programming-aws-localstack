package com.example.localstack.service;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.data.repository.CEPRepository;
import com.example.localstack.data.repository.CPFRepository;
import com.example.localstack.data.schema.CEP;
import com.example.localstack.data.schema.CPF;
import com.example.localstack.data.schema.Cliente;
import com.example.localstack.event.dto.ContratacaoMessage;
import com.example.localstack.event.publisher.Publisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContratacaoService {

    private final ClienteService clienteService;
    private final Publisher<ContratacaoMessage> contratacaoPublisher;
    private final CEPRepository cepRepository;
    private final CPFRepository cpfRepository;

    public void processarRequisicao(ContratacaoRequest input) {
        String clientName = input.cliente();

        CEP cep = cepRepository.save(new CEP(input.cep()));
        CPF cpf = cpfRepository.save(new CPF(input.cpf()));
        Cliente cliente = clienteService.salvar(new Cliente(clientName, cep, cpf));

        contratacaoPublisher.publish(
                new ContratacaoMessage(cliente.getId(), input.cpf(), input.cep())
        );
    }
}
