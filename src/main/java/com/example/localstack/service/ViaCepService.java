package com.example.localstack.service;

import com.example.localstack.service.dto.ViaCepResponse;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class ViaCepService {

    private final String viaCepUrl = "https://viacep.com.br/ws/";

    private final CompositeMeterRegistry meter;

    @Cacheable("viaCep")
    public ViaCepResponse consultar(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String queryUrl = viaCepUrl + cep + "/json/";

        meter.counter("viaCep.calls.counter", "cep", cep).increment();
        ResponseEntity<ViaCepResponse> response = meter.timer("viaCep.calls").record(() ->
                restTemplate.getForEntity(queryUrl, ViaCepResponse.class)
        );

        return response.getBody();
    }
}
