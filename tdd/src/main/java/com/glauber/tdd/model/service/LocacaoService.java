package com.glauber.tdd.model.service;

import com.glauber.tdd.exceptions.CarroForaDeEstoqueException;
import com.glauber.tdd.exceptions.NoCarroException;
import com.glauber.tdd.exceptions.NoClientException;
import com.glauber.tdd.model.Carro;
import com.glauber.tdd.model.Cliente;
import com.glauber.tdd.model.Locacao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocacaoService {


    public Locacao efetuarLocacao(Cliente cliente, Carro carro, LocalDate dataRetorno) {
        validarClienteECarro(cliente, carro);
        var dataDeRetornoValidada = validarEAjustarDataRetorno(dataRetorno);
        var locacao = new Locacao(cliente, carro, dataDeRetornoValidada);
        atualizarEstoque(carro);
        return locacao;
    }

    private LocalDate validarEAjustarDataRetorno(LocalDate dataRetorno) {
        if (dataRetorno == null) {
            dataRetorno = LocalDate.now().plusDays(1);
        }
        return dataRetorno;
    }

    private void validarClienteECarro(Cliente cliente, Carro carro) {
        if (cliente == null) {
            throw new NoClientException();
        }
        if (carro == null) {
            throw new NoCarroException();
        }
    }

    private void atualizarEstoque(Carro carro) {
        if (carro.getEstoque() < 1) {
            throw new CarroForaDeEstoqueException();
        }
        carro.setEstoque(carro.getEstoque() - 1);
    }

    public Locacao darBaixaNaLocacao(Locacao locacao, LocalDate retornadoEm) {
        var retornadoEmValidado = validarORetorno(retornadoEm);
        locacao.setRetornadoEm(retornadoEmValidado);
        calcularValorAPagar(locacao, retornadoEmValidado);
        return locacao;
    }

    private LocalDate validarORetorno(LocalDate retornadoEm) {
        if (retornadoEm == null) {
            retornadoEm = LocalDate.now();
        }
        return retornadoEm;
    }

    private void calcularValorAPagar(Locacao locacao, LocalDate retornadoEm) {
        var diasAlugados = locacao.getAlugadoEm().until(retornadoEm, ChronoUnit.DAYS);
        var valorAPagar = locacao.getValor() * diasAlugados;
        locacao.setValor(valorAPagar);
        locacao.setRetornadoEm(retornadoEm);
    }
}