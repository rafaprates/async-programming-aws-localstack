package com.example.localstack.data.repository;

import com.example.localstack.data.schema.CEPInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CEPInfoRepository extends JpaRepository<CEPInfo, Long> {
}
