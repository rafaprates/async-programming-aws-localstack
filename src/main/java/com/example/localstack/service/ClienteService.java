package com.example.localstack.service;

import com.example.localstack.data.repository.ClienteRepository;
import com.example.localstack.data.schema.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }
}
