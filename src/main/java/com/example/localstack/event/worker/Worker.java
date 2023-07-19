package com.example.localstack.event.worker;

public interface Worker<T> {

    void listen();
    void process(T message);
}
