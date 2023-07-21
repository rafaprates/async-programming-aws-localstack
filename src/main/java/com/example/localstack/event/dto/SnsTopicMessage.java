package com.example.localstack.event.dto;

public record SnsTopicMessage(
        String Type,
        String MessageId,
        String TopicArn,
        String Message,
        String Timestamp,
        String SignatureVersion,
        String Signature,
        String SigningCertURL,
        String UnsubscribeURL
) {
}
