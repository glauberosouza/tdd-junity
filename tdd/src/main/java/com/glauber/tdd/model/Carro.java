package com.glauber.tdd.model;

public class Carro {
    private String nome;
    private Integer estoque;
    private Double valorAluguel;

    public Carro(String nome, Integer estoque, Double valorAluguel) {
        this.nome = nome;
        this.estoque = estoque;
        this.valorAluguel = valorAluguel;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public Double getValorAluguel() {
        return valorAluguel;
    }
}