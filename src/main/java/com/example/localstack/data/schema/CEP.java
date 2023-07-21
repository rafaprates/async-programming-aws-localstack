package com.example.localstack.data.schema;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CEP {

    @Id
    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    public CEP(String cep) {
        this.cep = cep;
        this.logradouro = "";
        this.complemento = "";
        this.bairro = "";
        this.localidade = "";
        this.uf = "";
        this.ibge = "";
        this.gia = "";
        this.ddd = "";
        this.siafi = "";
    }

    public void update(String logradouro, String complemento, String bairro, String localidade, String uf, String ibge, String gia, String ddd, String siafi) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.ibge = ibge;
        this.gia = gia;
        this.ddd = ddd;
        this.siafi = siafi;
    }
}
