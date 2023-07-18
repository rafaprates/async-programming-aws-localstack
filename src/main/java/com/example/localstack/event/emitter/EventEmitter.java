package com.example.localstack.event.emitter;

public interface EventEmitter<T> {
    void emit(T message);
}
