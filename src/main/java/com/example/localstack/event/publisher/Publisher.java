package com.example.localstack.event.publisher;

public interface Publisher<T> {
    void publish(T message);
}
