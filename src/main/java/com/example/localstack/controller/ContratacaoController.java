package com.example.localstack.controller;

import com.example.localstack.controller.request.ContratacaoRequest;
import com.example.localstack.event.emitter.EventEmitter;
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

    private final EventEmitter<ContratacaoRequest> contratacaoEventEmitter;

    @PostMapping("/api/v1/contratacoes")
    public ResponseEntity<Void> contratar(@Valid @RequestBody ContratacaoRequest request) {
        contratacaoEventEmitter.emit(request);
        return ResponseEntity.accepted().build();
    }
}
