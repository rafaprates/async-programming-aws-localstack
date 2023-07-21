# Asynchronous AWS Messaging Project 

## Introduction
This Java project demonstrates asynchronous programming using AWS services like AWS SQS (Simple Queue Service) and AWS SNS (Simple Notification Service) along with Redis for caching. It leverages LocalStack to emulate the AWS environment locally, allowing you to test and develop without incurring any actual AWS costs.

## System Architecture
![System Architecture](docs/system-architecture.png)

### AWS SNS
SNS is used to receive a HTTP POST request and publish the messages to a topic (contratacao-topic) that distributes messages to multiple queues (process-cep-queue, process-cpf-queue). The queues are subscribed to the topic and receive the messages asynchronously. The topic is created on Docker start up.

### AWS SQS
SQS is used to receive messages from the topic and process them asynchronously. The messages are processed by the CEPWorker and CPFWorker classes, both of which implement the Worker Interface. The queues are created on Docker start up.

### Redis Caching
Redis is used to improve performance of the application and potentially reduce costs. Before going to an external API (ViaCep), the application checks if the CEP has already been processed and cached in Redis. If it has, the application returns the cached result. If not, the application calls the external API and caches the result in Redis. 
