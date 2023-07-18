package com.example.localstack.event.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SnsTopicMessage {
    public String Type;
    public String MessageId;
    public String TopicArn;
    public String Message;
    public String Timestamp;
    public String SignatureVersion;
    public String Signature;
    public String SigningCertURL;
    public String UnsubscribeURL;
}
