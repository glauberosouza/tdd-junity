package com.glauber.tdd.controller;

import com.glauber.tdd.model.Estudante;
import com.glauber.tdd.model.service.impl.EstudanteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    EstudanteServiceImpl estudanteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody Estudante estudante) {
        var estudanteCadastrado = estudanteService.cadastrarEstudante(estudante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudanteCadastrado);
    }

    @PutMapping("/{estudanteId}")
    public ResponseEntity<Estudante> atualizarEstudante(@PathVariable Long estudanteId, @RequestBody Estudante estudante){
        var estudanteAtualizado = estudanteService.atualizarEstudante(estudanteId, estudante);
        return ResponseEntity.status(HttpStatus.OK).body(estudanteAtualizado);
    }

    @DeleteMapping("/{estudanteId}")
    public ResponseEntity<Void> deletarEstudante(@PathVariable Long estudanteId) {
        estudanteService.deletarEstudante(estudanteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}