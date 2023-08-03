package com.glauber.tdd.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocacaoService {


    public Locacao alugar(Cliente cliente, Carro carro, LocalDate dataRetorno) {
        validarCampos(cliente, carro);
        // Retirei a validação da data de retorno daqui a coloquei em um método especifico pra isso.
        // Motivo -> O método alugar não deve fazer validações e sim fazer apenas o que a locação demanda.
        var dataDeRetornoValidada = dataDeRetornoInformado(dataRetorno);
        var locacao = new Locacao(cliente, carro, dataDeRetornoValidada);
        updateEstoque(carro);
        return locacao;
    }

    private LocalDate dataDeRetornoInformado(LocalDate dataRetorno) {
        if (dataRetorno == null) {
            dataRetorno = LocalDate.now().plusDays(1);
        }
        return dataRetorno;
    }

    private void validarCampos(Cliente cliente, Carro carro) {
        if (cliente == null) {
            throw new NoClientException();
        }
        if (carro == null) {
            throw new NoCarroException();
        }
    }


    private void updateEstoque(Carro carro) {
        if (carro.getEstoque() < 1) {
            return;
        }
        carro.setEstoque(carro.getEstoque() - 1);
    }

    public Locacao darBaixa(Locacao locacao, LocalDate retornadoEm) {
        // Retirei a validação do retornado em caso nulo daqui e coloquei dentro de outro método.
        var retornadoEmValidado = validarORetornadoEm(retornadoEm);
        calcularOValorDaLocacao(locacao, retornadoEmValidado);
        locacao.setRetornadoEm(retornadoEmValidado);
        return locacao;
    }

    private LocalDate validarORetornadoEm(LocalDate retornadoEm) {
        if (retornadoEm == null) {
            retornadoEm = LocalDate.now();
        }
        return retornadoEm;
    }

    private void calcularOValorDaLocacao(Locacao locacao, LocalDate retornadoEm) {
        var diasAlugados = locacao.getAlugadoEm().until(retornadoEm, ChronoUnit.DAYS);
        var valorAPagar = locacao.getValor() * diasAlugados;
        locacao.setValor(valorAPagar);
        locacao.setRetornadoEm(retornadoEm);
    }
}