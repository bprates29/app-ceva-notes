package br.com.cevanotes.utils;

import br.com.cevanotes.model.Rotulo;

import java.time.LocalDate;

public class RotuloBuilder {
    private int id = 1;
    private String nome = "IPA Puro Malte";
    private String estilo = "IPA";
    private double teorAlcoolico = 6.7;
    private String cervejaria = "Cervejaria Alpha";
    private LocalDate dataCadastro = LocalDate.now();

    public static RotuloBuilder umRotulo() {
        return new RotuloBuilder();
    }

    public RotuloBuilder comId(int id) {
        this.id = id;
        return this;
    }

    public RotuloBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public RotuloBuilder comEstilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public RotuloBuilder comTeorAlcoolico(double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
        return this;
    }

    public RotuloBuilder comCervejaria(String cervejaria) {
        this.cervejaria = cervejaria;
        return this;
    }

    public RotuloBuilder comDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public Rotulo build() {
        return new Rotulo(id, nome, estilo, teorAlcoolico, cervejaria, dataCadastro);
    }
}
