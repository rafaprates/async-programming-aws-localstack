package com.example.localstack.service;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.controller.response.ViaCepResponse;
import com.example.localstack.data.schema.CEPInfo;
import com.example.localstack.data.schema.CPFInfo;
import com.example.localstack.data.schema.Cliente;
import com.example.localstack.event.dto.ContratacaoMessage;
import com.example.localstack.event.emitter.Publisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContratacaoService {

    private final ViaCepService viaCepService;
    private final ClienteService clienteService;
    private final Publisher<ContratacaoMessage> contratacaoPublisher;

    public void processarRequisicao(ContratacaoRequest input) {
        CPFInfo cpf = new CPFInfo(input.getCpf());
        CEPInfo cep = new CEPInfo(input.getCep());
        Cliente cliente = new Cliente(input.getCliente(), cep, cpf);

        Cliente salvar = clienteService.salvar(cliente);

        contratacaoPublisher.publish(new ContratacaoMessage(salvar.getId()));
    }

    public void processarCep(ContratacaoMessage message) {
        log.info("Processar CEP para cliente {}", message.getIdCliente());
        Cliente cliente = clienteService.buscarPorId(message.getIdCliente());

        ViaCepResponse response = viaCepService.consultar(cliente.getCepInfo().getCep());

        CEPInfo cepInfo = CEPInfo.builder()
                .cep(cliente.getCepInfo().getCep())
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

        cliente.setCepInfo(cepInfo);

        clienteService.salvar(cliente);
    }

    public void processarCpf(ContratacaoMessage message) {
        log.info("Processar CPF para cliente {}", message.getIdCliente());
    }
}
