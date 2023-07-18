package com.example.localstack.config;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
public class AwsCredentialsImpl implements AwsCredentials {

    @Override
    public String accessKeyId() {
        return "FAKE";
    }

    @Override
    public String secretAccessKey() {
        return "FAKE";
    }
}
