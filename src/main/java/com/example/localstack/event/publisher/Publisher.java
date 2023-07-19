package com.example.localstack.event.emitter;

public interface Publisher<T> {
    void publish(T message);
}
