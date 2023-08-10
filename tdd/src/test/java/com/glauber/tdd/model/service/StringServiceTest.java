package com.glauber.tdd.model.service;

import com.glauber.tdd.model.service.StringService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringServiceTest {
    @Test
    @DisplayName("Remove espaços em branco e deixa apenas a primeira letra em maiusculo.")
    void deveFormatarOtexto() {
        //ARRANGE
        var text = "ALL thOse momEnts wiLL be loSt in Time, LIKE tEars in rain...";
        //ACT
        var stringService = new StringService();
        var textBeautiful = stringService.beautify(text);
        //ASSERTIONS
        Assertions.assertEquals("All those moments will be lost in time, like tears in rain...", textBeautiful);
    }
}