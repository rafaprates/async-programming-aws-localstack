package com.example.localstack.data.schema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cep_id")
    private CEP cep;

    @OneToOne
    @JoinColumn(name = "cpf_id")
    private CPF cpf;

    public Cliente(String name) {
        this.name = name;
    }

    public Cliente(String name, CEP cep, CPF cpf) {
        this.name = name;
        this.cep = cep;
        this.cpf = cpf;
    }
}
