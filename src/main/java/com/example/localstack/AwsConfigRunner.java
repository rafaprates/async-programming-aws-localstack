package com.example.localstack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AwsConfigRunner implements CommandLineRunner {

    private final SnsClient snsClient;

    @Override
    public void run(String... args) throws Exception {
        log.info("Subscribing to SNS topics...");

        SubscribeRequest subscricaoCep = SubscribeRequest.builder()
                .protocol("sqs")
                .endpoint("arn:aws:sqs:us-east-1:000000000000:consulta-cep-queue")
                .topicArn("arn:aws:sns:us-east-1:000000000000:contratacao")
                .build();

        SubscribeRequest subscricaoCpf = SubscribeRequest.builder()
                .protocol("sqs")
                .endpoint("arn:aws:sqs:us-east-1:000000000000:consulta-cpf-queue")
                .topicArn("arn:aws:sns:us-east-1:000000000000:contratacao")
                .build();

        snsClient.subscribe(subscricaoCep);
        snsClient.subscribe(subscricaoCpf);
    }
}
