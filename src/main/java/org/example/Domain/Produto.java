package org.example.Domain;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Produto extends Base {
    private String codigo;
    private String nome;
    private BigDecimal preco;

    public Produto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        Objects.requireNonNull(preco, "O preço é obrigatório.");
        if (preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }

    public abstract String getTipo();
}
