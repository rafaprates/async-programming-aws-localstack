package com.example.localstack.data.repository;

import com.example.localstack.data.schema.CEP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CEPRepository extends JpaRepository<CEP, String> {
}
