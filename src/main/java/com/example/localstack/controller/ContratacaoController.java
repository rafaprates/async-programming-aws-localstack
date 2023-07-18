package com.example.localstack.controller;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.event.emitter.EventEmitter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContratacaoController {

    private final EventEmitter<ContratacaoRequest> contratacaoEventEmitter;

    @PostMapping("/api/v1/contratacoes")
    public void contratar(@Valid @RequestBody ContratacaoRequest request) {
//        log.info("Contratação recebida: {}", request);
        contratacaoEventEmitter.emit(request);
    }
}
