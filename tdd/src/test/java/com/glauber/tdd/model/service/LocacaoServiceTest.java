package com.glauber.tdd.model.service;

import com.glauber.tdd.exceptions.CarroForaDeEstoqueException;
import com.glauber.tdd.exceptions.NoCarroException;
import com.glauber.tdd.exceptions.NoClientException;
import com.glauber.tdd.model.Carro;
import com.glauber.tdd.model.Cliente;
import com.glauber.tdd.model.Locacao;
import com.glauber.tdd.model.repository.LocacaoDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocacaoServiceTest {

    @Mock
    private LocacaoDAO locacaoDAO;
    @InjectMocks
    private LocacaoService locacaoService;

    @Test
    @DisplayName("Deve alugar o carro quando todos os campos estiverem ok")
    public void deveAlugarCarro() {
        //Arrange -> Preparar o teste
        var cliente = new Cliente("João");
        var carro = new Carro("Gol", 2, 100.00);


        // Act -> Ação
        //var servico = new LocacaoService();
        var locacao = locacaoService.efetuarLocacao(cliente, carro, LocalDate.now().plusDays(1));

        //Mockito.doNothing().when(locacaoDAO).save(isA(Locacao.class));

        //Assert -> Verificar as asserções
        verify(locacaoDAO, times(1)).save(locacao);
        assertEquals(100.00, locacao.getValor());
        assertEquals("Gol", locacao.getCarro().getNome());
        assertEquals("João", locacao.getCliente().getNome());
        assertEquals(1, locacao.getCarro().getEstoque());
        assertEquals(LocalDate.now(), locacao.getAlugadoEm());
        assertEquals(LocalDate.now().plusDays(1), locacao.getRetornarEm());
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
        assertEquals(locacao.getRetornarEm(), LocalDate.now().plusDays(1));
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
        assertEquals(LocalDate.now().plusDays(1), locacaoBaixada.getRetornadoEm());
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
        assertEquals(1, carro.getEstoque());
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
        assertEquals(450.00, locacaoBaixada.getValor());
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
        assertEquals(LocalDate.now(), locacaoBaixada.getRetornadoEm());
    }
}