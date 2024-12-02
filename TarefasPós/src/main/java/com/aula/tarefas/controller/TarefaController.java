package com.aula.tarefas.controller;

import com.aula.tarefas.dto.TarefaDto;
import com.aula.tarefas.model.Tarefa;
import com.aula.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaService.listarTodas();
    }

    @GetMapping("/deletadas")
    public List<Tarefa> listarDeletadas() {
        return tarefaService.buscarDeletadas();
    }

    @PostMapping
    public Tarefa criar(@Valid @RequestBody TarefaDto tarefaDto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaDto.getTitulo());
        tarefa.setDescricao(tarefaDto.getDescricao());
        tarefa.setPrioridade(tarefaDto.getPrioridade());
        return tarefaService.criarTarefaParaUsuario(tarefa, tarefaDto.getUsuarioId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable String id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{titulo}")
    public List<Tarefa> buscarPorTitulo(@PathVariable String titulo) {
        return tarefaService.buscarPorTitulo(titulo);
    }

    @GetMapping("/intervalo/{dataCriacao}")
    public List<Tarefa> buscarPorIntervaloDeDatas(
            @PathVariable("dataCriacao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataCriacao,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return tarefaService.buscarPorIntervaloDeDatas(dataCriacao, dataFim);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable String id, @Valid @RequestBody TarefaDto tarefaDto) {
        Optional<Tarefa> tarefaOpt = tarefaService.buscarPorId(id).map(tarefaExistente -> {
            tarefaExistente.setTitulo(tarefaDto.getTitulo());
            tarefaExistente.setDescricao(tarefaDto.getDescricao());
            tarefaExistente.setPrioridade(tarefaDto.getPrioridade());
            return tarefaService.salvar(tarefaExistente);
        });

        return tarefaOpt.isPresent() ? ResponseEntity.ok(tarefaOpt.get()) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> deletar(@PathVariable String id) {
        Tarefa tarefaDeletada = tarefaService.deletar(id);
        return tarefaDeletada != null ? ResponseEntity.ok(tarefaDeletada) : ResponseEntity.notFound().build();
    }
}
