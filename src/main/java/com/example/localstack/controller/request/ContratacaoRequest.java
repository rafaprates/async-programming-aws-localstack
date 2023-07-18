package com.example.localstack.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContratacaoRequest {

    @NotBlank
    private String cliente;

    @Size(min = 8, max = 8)
    private String cep;
}
