package com.glauber.tdd.model.service;

import com.glauber.tdd.model.Estudante;

import java.util.Optional;

public interface EstudanteService {
    Estudante cadastrarEstudante(Estudante estudante);

    Estudante atualizarEstudante(Long estudanteId, Estudante estudante);
    void deletarEstudante(Long estudanteId);
}