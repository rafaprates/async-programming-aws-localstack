package com.example.localstack.data.repository;

import com.example.localstack.data.schema.CPFInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CPFInfoRepository extends JpaRepository<CPFInfo, String> {
}
