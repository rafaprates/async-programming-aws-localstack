package com.example.localstack.service;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.controller.response.ViaCepResponse;
import com.example.localstack.data.schema.CEPInfo;
import com.example.localstack.data.schema.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContratacaoService {

    private final ViaCepService viaCepService;
    private final ClienteService clienteService;

    public ContratacaoService(ViaCepService viaCepService, ClienteService clienteService) {
        this.viaCepService = viaCepService;
        this.clienteService = clienteService;
    }

    public void processar(ContratacaoRequest input) {
        ViaCepResponse response = viaCepService.consultar(input.getCep());

        CEPInfo cepInfo = CEPInfo.builder()
                .cep(response.getCep())
                .logradouro(response.getLogradouro())
                .bairro(response.getBairro())
                .localidade(response.getLocalidade())
                .uf(response.getUf())
                .ibge(response.getIbge())
                .gia(response.getGia())
                .ddd(response.getDdd())
                .siafi(response.getSiafi())
                .build();

        Cliente cliente = new Cliente(input.getCliente(), cepInfo);

        clienteService.salvar(cliente);
    }
}
