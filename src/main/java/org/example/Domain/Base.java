package org.example.Domain;

import java.time.LocalDate;

public abstract class Base {
    private String criadoPor;
    private LocalDate dataCriacao;

    public Base() {
        this.dataCriacao = LocalDate.now();
    }

    public Base(String criadoPor, LocalDate dataCriacao) {
        this.criadoPor = criadoPor;
        this.dataCriacao = dataCriacao;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
