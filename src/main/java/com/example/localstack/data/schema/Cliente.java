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

    @ManyToOne(cascade = CascadeType.ALL)
    private CEPInfo cepInfo;

    public Cliente(String name, CEPInfo cepInfo) {
        this.name = name;
        this.cepInfo = cepInfo;
    }
}
