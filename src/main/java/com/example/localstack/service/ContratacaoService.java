package com.example.localstack.service;

import com.example.localstack.controller.request.ContratacaoRequest;
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

    public void processarRequisicao(ContratacaoRequest input) {
        CPF cpf = new CPF(input.getCpf());
        CEP cep = new CEP(input.getCep());
        Cliente cliente = new Cliente(input.getCliente(), cep, cpf);

        Cliente salvar = clienteService.salvar(cliente);

        contratacaoPublisher.publish(new ContratacaoMessage(salvar.getId()));
    }
}
