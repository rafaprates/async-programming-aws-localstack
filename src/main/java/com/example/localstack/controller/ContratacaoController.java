package com.example.localstack.controller;

import com.example.localstack.emitter.EventEmitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContratacaoController {

    private final EventEmitter contratacaoEventEmitter;

    @PostMapping("/api/v1/contratacoes")
    public void contratar() {
        contratacaoEventEmitter.emit("Hello world!");
    }
}
