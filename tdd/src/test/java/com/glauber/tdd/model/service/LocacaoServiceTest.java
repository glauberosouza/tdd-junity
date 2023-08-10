package com.glauber.tdd.model.service;

import com.glauber.tdd.exceptions.CarroForaDeEstoqueException;
import com.glauber.tdd.exceptions.NoCarroException;
import com.glauber.tdd.exceptions.NoClientException;
import com.glauber.tdd.model.Carro;
import com.glauber.tdd.model.Cliente;
import com.glauber.tdd.model.service.LocacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class LocacaoServiceTest {
    @Test
    @DisplayName("Deve alugar o carro quando todos os campos estiverem ok")
    public void deveAlugarCarro() {
        //Arrange -> Preparar o teste
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 100.00);


        // Act -> Ação
        var servico = new LocacaoService();
        var locacao = servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));


        //Assert -> Verificar as asserções
        Assertions.assertEquals(100.00, locacao.getValor());
        Assertions.assertEquals("Gol", locacao.getCarro().getNome());
        Assertions.assertEquals("João", locacao.getCliente().getNome());
        Assertions.assertEquals(1, locacao.getCarro().getEstoque());
        Assertions.assertEquals(LocalDate.now(), locacao.getAlugadoEm());
        Assertions.assertEquals(LocalDate.now().plusDays(1), locacao.getRetornarEm());
    }

    @Test
    @DisplayName("Lança exceção quando o cliente for nulo")
    public void deveLancarExcecaoCasoClienteNulo() {
        //ARRANGE
        var cliente = new Cliente(null);
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        //ASSERT
        Assertions.assertThrows(
                NoClientException.class,
                () -> servico.efetuarLocacao(null, carro, LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Lança exceção quando o carro for nulo")
    public void lancaExcecaoQuandoOValorDoCarroForNulo() {
        //ARRANGE
        var cliente = new Cliente("João");
        Carro carro = null;
        var servico = new LocacaoService();
        //ACT
        //ASSERT
        Assertions.assertThrows(
                NoCarroException.class,
                () -> servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Configura a data a ser retornada para o dia posterior quando a mesma não for informada")
    public void configuraADataASerRetornadaParaODiaPosteriorQuandoAMesmaNaoForInformada() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        var locacao = servico.efetuarLocacao(cliente, carro, null);
        //ASSERT
        Assertions.assertEquals(locacao.getRetornarEm(), LocalDate.now().plusDays(1));
    }

    @Test
    @DisplayName("Deve dar baixa no aluguel do carro")
    public void deveDarBaixaNoAluguelDoCarro() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        var locacao = servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));
        var locacaoBaixada = servico.darBaixaNaLocacao(locacao, LocalDate.now().plusDays(1));
        //ASSERT
        Assertions.assertEquals(LocalDate.now().plusDays(1), locacaoBaixada.getRetornadoEm());
    }

    @Test
    @DisplayName("Deve diminuir o estoque do carro quando realizar a locação")
    public void deveDiminuirOEstoqueDoCarroQuandoRealizarALocacao() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        var locacao = servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));
        //ASSERT
        Assertions.assertEquals(1, carro.getEstoque());
    }

    @Test
    @DisplayName("Deve dar baixa no aluguel do carro e calcular o valor correto a pagar")
    public void deveDarBaixaNoAluguelDoCarroECalcularOValorCorretoAPagar() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        var locacao = servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));
        var locacaoBaixada = servico.darBaixaNaLocacao(locacao, LocalDate.now().plusDays(3));
        //ASSERT
        Assertions.assertEquals(450.00, locacaoBaixada.getValor());
    }

    @Test
    @DisplayName("Deve lançar uma exception caso não tenha o carro escolhido em estoque.")
    public void deveLancarUmaExceptionCasoNaoTenhaOCarroEmEstoque() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 0, 150.00);
        var servico = new LocacaoService();

        //ACT & ASSERT
        Assertions.assertThrows(CarroForaDeEstoqueException.class,
                () -> servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Configura a data de retorno na baixa para o dia corrente caso a mesma não for informada")
    public void configuraADataDeretornoParaoDiaCorrenteCasoAMesmaNaoForInformada() {
        //ARRANGE
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 150.00);
        var servico = new LocacaoService();
        //ACT
        var locacao = servico.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));
        var locacaoBaixada = servico.darBaixaNaLocacao(locacao, null);
        //ASSERT
        Assertions.assertEquals(LocalDate.now(), locacaoBaixada.getRetornadoEm());
    }
}