package com.example.localstack.config;

import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

@Configuration
public class AwsCredentialsProviderImpl implements AwsCredentialsProvider {

    private final AwsCredentialsImpl awsCredentialsImpl;

    public AwsCredentialsProviderImpl(AwsCredentialsImpl awsCredentialsImpl) {
        this.awsCredentialsImpl = awsCredentialsImpl;
    }

    @Override
    public AwsCredentialsImpl resolveCredentials() {
        return awsCredentialsImpl;
    }
}
