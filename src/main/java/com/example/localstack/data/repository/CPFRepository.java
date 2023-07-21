package com.example.localstack.data.repository;

import com.example.localstack.data.schema.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CPFRepository extends JpaRepository<CPF, String> {
}
