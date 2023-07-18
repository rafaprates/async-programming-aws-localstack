package com.example.localstack.data.repository;

import com.example.localstack.data.schema.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
