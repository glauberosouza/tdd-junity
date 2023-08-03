package com.glauber.tdd.model;

import java.time.LocalDate;

public class Locacao {
    private Cliente cliente;
    private Carro carro;
    private LocalDate alugadoEm;
    private LocalDate retornarEm;
    private LocalDate retornadoEm;
    private Double valor;

    public Locacao(Cliente cliente, Carro carro, LocalDate retornarEm) {
        this.cliente = cliente;
        this.carro = carro;
        this.alugadoEm = LocalDate.now();
        this.retornarEm = retornarEm;
        this.valor = carro.getValorAluguel();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public LocalDate getAlugadoEm() {
        return alugadoEm;
    }

    public void setAlugadoEm(LocalDate alugadoEm) {
        this.alugadoEm = alugadoEm;
    }

    public LocalDate getRetornarEm() {
        return retornarEm;
    }

    public void setRetornarEm(LocalDate retornarEm) {
        this.retornarEm = retornarEm;
    }

    public LocalDate getRetornadoEm() {
        return retornadoEm;
    }

    public void setRetornadoEm(LocalDate retornadoEm) {
        this.retornadoEm = retornadoEm;
    }

    public Double getValor(){
        return this.valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}