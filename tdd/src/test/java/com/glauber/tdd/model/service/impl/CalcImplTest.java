package com.glauber.tdd.model.service.impl;

import com.glauber.tdd.model.repository.Calc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalcImplTest {
    @Spy // Quando não encontra o mock, é chamado realmente a implementação.
    private CalcImpl calcSpy;
    @Mock // Cria uma instancia de uma classe, porém Mockada. Quando chamo um método, apenas simulo o mesmo.
    private Calc calcMock;

    @Test
    @DisplayName("Deve somar 2 numeros inteiros")
    public void deveSomarDoisNumerosInteiros() {
        Mockito.when(calcMock.somar(1, 2)).thenReturn(3);
        Mockito.doReturn(3).when(calcSpy).somar(1, 2);
        assertEquals(3, calcMock.somar(1, 2));
        assertEquals(3, calcSpy.somar(1, 2));
        assertEquals(7, calcSpy.somar(1, 6));
    }
}