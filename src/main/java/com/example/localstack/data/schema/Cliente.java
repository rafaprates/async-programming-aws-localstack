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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cep_id")
    private CEPInfo cepInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf_id")
    private CPFInfo cpfInfo;

    public Cliente(String name, CEPInfo cepInfo, CPFInfo cpfInfo) {
        this.name = name;
        this.cepInfo = cepInfo;
        this.cpfInfo = cpfInfo;
    }
}
