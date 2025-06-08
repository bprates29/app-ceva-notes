package br.com.cevanotes.utils;

import br.com.cevanotes.dto.RotuloDTO;

public class RotuloDTOBuilder {
    private String nome = "IPA Puro Malte";
    private String estilo = "IPA";
    private double teorAlcoolico = 6.7;
    private String cervejaria = "Cervejaria Alpha";

    public static RotuloDTOBuilder umRotuloDTO() {
        return new RotuloDTOBuilder();
    }

    public RotuloDTOBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public RotuloDTOBuilder comEstilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public RotuloDTOBuilder comTeorAlcoolico(double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
        return this;
    }

    public RotuloDTOBuilder comCervejaria(String cervejaria) {
        this.cervejaria = cervejaria;
        return this;
    }

    public RotuloDTOBuilder vazio() {
        this.nome = "";
        this.estilo = "";
        this.teorAlcoolico = 0.0;
        this.cervejaria = "";
        return this;
    }

    public RotuloDTOBuilder nulo() {
        this.nome = null;
        this.estilo = null;
        this.teorAlcoolico = 0.0;
        this.cervejaria = null;
        return this;
    }

    public RotuloDTO build() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome(nome);
        dto.setEstilo(estilo);
        dto.setTeorAlcoolico(teorAlcoolico);
        dto.setCervejaria(cervejaria);
        return dto;
    }
}
