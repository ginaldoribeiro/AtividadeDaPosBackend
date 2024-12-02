package com.aula.tarefas.service;

import com.aula.tarefas.model.Tarefa;
import com.aula.tarefas.model.Usuario;
import com.aula.tarefas.repository.TarefaRepository;
import com.aula.tarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findByDeletadoFalse();
    }

    public List<Tarefa> buscarDeletadas() {
        return tarefaRepository.findByDeletadoTrue();
    }

    public Optional<Tarefa> buscarPorId(String id) {
        return tarefaRepository.findById(id);
    }

    public List<Tarefa> buscarPorTitulo(String titulo) {
        return tarefaRepository.findByTituloContainingAndDeletadoFalse(titulo);
    }

    public List<Tarefa> buscarPorIntervaloDeDatas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return tarefaRepository.findByDataCriacaoBetweenAndDeletadoFalse(dataInicio, dataFim);
    }

    public Tarefa salvar(Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDateTime.now());
        return tarefaRepository.save(tarefa);
    }

    public Tarefa deletar(String id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            Tarefa tarefaDeletada = tarefa.get();
            tarefaDeletada.setDeletado(true);
            return tarefaRepository.save(tarefaDeletada);
        }
        return null;
    }

    public Tarefa criarTarefaParaUsuario(Tarefa tarefa, String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            tarefa.setUsuario(usuarioOpt.get());
            tarefa.setDataCriacao(LocalDateTime.now());
            return tarefaRepository.save(tarefa);
        }
        return null;
    }
}