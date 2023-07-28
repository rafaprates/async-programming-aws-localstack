package com.example.localstack.controller;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.service.ContratacaoService;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContratacaoController {

    private final ContratacaoService contratacaoService;

    @Timed(value = "contratacao.calls", extraTags = {"url", "/api/v1/contratacoes"})
    @PostMapping("/api/v1/contratacoes")
    public ResponseEntity<Void> contratar(@Valid @RequestBody ContratacaoRequest input) {
        contratacaoService.processarRequisicao(input);
        return ResponseEntity.accepted().build();
    }
}
