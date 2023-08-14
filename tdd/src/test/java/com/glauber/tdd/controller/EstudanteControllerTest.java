package com.glauber.tdd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.tdd.TddApplication;
import com.glauber.tdd.model.Estudante;
import com.glauber.tdd.model.repository.EstudanteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TddApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EstudanteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EstudanteRepository repository;


    @Test
    @DisplayName("Deve salvar o estudante")
    void deveSalvarOEstudanteNoBancoDeDados() throws Exception {
        //ARRANGE
        var estudante = new Estudante("Gabriel");
        var objectMapper = new ObjectMapper();
        var body = objectMapper.writeValueAsString(estudante);
        //ACT
        mockMvc.perform(
                post("/estudantes/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)

        ).andExpect(status().is2xxSuccessful());
        //ASSERT
        List<Estudante> todosEstudantes = repository.findAll();
        Assertions.assertEquals(1, todosEstudantes.size());
        Assertions.assertEquals("Gabriel", estudante.getNome());
    }

    @Test
    @DisplayName("Deve deletar um estudante pelo id")
    void deveDeletarUmEstudantePeloId() throws Exception {
        //ARRANGE
        var estudante = new Estudante("Joao");
        repository.save(estudante);
        //ACT
        mockMvc.perform(
                delete("/estudantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());
        //ASSERT
        List<Estudante> estudantes = repository.findAll();
        Assertions.assertTrue(estudantes.isEmpty());
    }

    @Test
    @Sql("classpath:db/sql/insert_into_students.sql")
    @DisplayName("Deve atualizar o nome do estudante")
    void deveAtualizaONomeDoEstudante() throws Exception {
        //ARRANGE
        var estudante = new Estudante("Glauber");
        var objectMapper = new ObjectMapper();
        var body = objectMapper.writeValueAsString(estudante);
        //ACT
        mockMvc.perform(
                put("/estudantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().is2xxSuccessful());
        //ASSERT
        Optional<Estudante> estudanteById = repository.findById(1L);
        Assertions.assertEquals("Glauber", estudanteById.get().getNome());
        assertThat(estudanteById.get().getNome(), is("Glauber"));
    }
}