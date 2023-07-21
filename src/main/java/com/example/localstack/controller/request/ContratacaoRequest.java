package com.example.localstack.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContratacaoRequest(
        @NotBlank
        String cliente,

        @Size(min = 8, max = 8)
        String cep,

        @Size(min = 11, max = 11)
        String cpf

) {
}
