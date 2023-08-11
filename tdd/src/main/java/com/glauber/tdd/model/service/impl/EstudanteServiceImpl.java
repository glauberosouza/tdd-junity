package com.glauber.tdd.model.service.impl;

import com.glauber.tdd.exceptions.EstudanteInvalidoException;
import com.glauber.tdd.model.Estudante;
import com.glauber.tdd.model.repository.EstudanteRepository;
import com.glauber.tdd.model.service.EstudanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstudanteServiceImpl implements EstudanteService {

    @Autowired
    EstudanteRepository repository;

    @Override
    public Estudante cadastrarEstudante(Estudante estudante) {
        return repository.save(estudante);
    }

    @Override
    public Estudante atualizarEstudante(Long estudanteId, Estudante estudante) {
        var estudanteById = repository.findById(estudanteId).orElseThrow();
        estudanteById.setNome(estudante.getNome());
        Estudante estudanteAtualizado = repository.save(estudanteById);
        return estudanteAtualizado;
    }

    @Override
    public void deletarEstudante(Long estudanteId) {
        Optional<Estudante> estudanteById = repository.findById(estudanteId);
        if (estudanteById.isPresent()) {
            repository.deleteById(estudanteId);
        } else {
            throw new EstudanteInvalidoException();
        }
    }
}