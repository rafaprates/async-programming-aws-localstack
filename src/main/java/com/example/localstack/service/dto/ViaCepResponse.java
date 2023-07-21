package com.example.localstack.service.dto;

import java.io.Serializable;

public record ViaCepResponse(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String ibge,
        String gia,
        String ddd,
        String siafi

) implements Serializable {
}
