package com.example.localstack.service;

import com.example.localstack.service.dto.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ViaCepService {

    @Cacheable("viaCep")
    public ViaCepResponse consultar(String cep) {
        long start = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        String viaCepUrl = "https://viacep.com.br/ws/" + cep + "/json/";
        ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(viaCepUrl, ViaCepResponse.class);
        log.info("Tempo de resposta da consulta ao ViaCep: {} ms", System.currentTimeMillis() - start);
        return response.getBody();
    }
}
