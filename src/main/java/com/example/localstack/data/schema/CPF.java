package com.example.localstack.data.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CPF {

    @Id
    private String cpf;

    private boolean isValido = false;

    public CPF(String cpf) {
        this.cpf = cpf;
    }
}
