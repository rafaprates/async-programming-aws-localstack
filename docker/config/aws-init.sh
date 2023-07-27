#!/bin/sh
cat "Creating topic..."
awslocal sns create-topic --name contratacao

cat "Creating queues..."
awslocal sqs create-queue --queue-name consulta-cep-queue
awslocal sqs create-queue --queue-name consulta-cpf-queue

cat "Subscribing queues to topic..."
awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:contratacao --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:consulta-cep-queue
awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:contratacao --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:consulta-cpf-queue
