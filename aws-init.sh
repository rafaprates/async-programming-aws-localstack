#!/bin/sh
awslocal sns create-topic --name contratacao
awslocal sqs create-queue --queue-name consulta-cep-queue
awslocal sqs create-queue --queue-name consulta-cpf-queue
