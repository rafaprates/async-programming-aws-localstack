package com.example.localstack.event.publisher;

import com.example.localstack.event.dto.ContratacaoMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ContratacaoPublisher implements Publisher<ContratacaoMessage> {

    private final SnsClient snsClient;

    @Override
    public void publish(ContratacaoMessage message) {
        String json = toJson(message);

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(json)
                    .topicArn("arn:aws:sns:us-east-1:000000000000:contratacao")
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private String toJson(ContratacaoMessage message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
