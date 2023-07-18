package com.example.localstack.service;

import com.example.localstack.controller.response.ViaCepResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ViaCepService {

    public ViaCepResponse consultar(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String viaCepUrl = "https://viacep.com.br/ws/" + cep + "/json/";
        ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(viaCepUrl, ViaCepResponse.class);
        return response.getBody();
    }
}
