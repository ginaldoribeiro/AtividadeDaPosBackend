package com.aula.tarefas.repository;

import com.aula.tarefas.model.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {
    List<Tarefa> findByTituloContainingAndDeletadoFalse(String titulo);
    List<Tarefa> findByDataCriacaoBetweenAndDeletadoFalse(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Tarefa> findByDeletadoTrue();
    List<Tarefa> findByDeletadoFalse();
    List<Tarefa> findAllById(List<String> ids);
    List<Tarefa> findByUsuarioIdAndDeletadoFalse(String usuarioId);
}