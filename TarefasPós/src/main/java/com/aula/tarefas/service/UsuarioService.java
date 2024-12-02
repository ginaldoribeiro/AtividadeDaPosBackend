package com.aula.tarefas.service;

import com.aula.tarefas.dto.TarefaDto;
import com.aula.tarefas.dto.UsuarioDto;
import com.aula.tarefas.model.Tarefa;
import com.aula.tarefas.model.Usuario;
import com.aula.tarefas.repository.TarefaRepository;
import com.aula.tarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<UsuarioDto> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setNome(usuario.getNome());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setTelefone(usuario.getTelefone());


            List<TarefaDto> tarefas = tarefaRepository.findByUsuarioIdAndDeletadoFalse(usuario.getId())
                    .stream()
                    .map(tarefa -> {
                        TarefaDto tarefaDto = new TarefaDto();
                        tarefaDto.setTitulo(tarefa.getTitulo());
                        tarefaDto.setDescricao(tarefa.getDescricao());
                        tarefaDto.setPrioridade(tarefa.getPrioridade());
                        return tarefaDto;
                    })
                    .collect(Collectors.toList());

            usuarioDto.setTarefas(tarefas);
            return usuarioDto;
        }).collect(Collectors.toList());
    }
    public Optional<UsuarioDto> buscarPorId(String id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setNome(usuario.getNome());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setTelefone(usuario.getTelefone());

            // Busca e mapeia as tarefas do usuário para TarefaDto
            List<TarefaDto> tarefas = tarefaRepository.findByUsuarioIdAndDeletadoFalse(usuario.getId())
                    .stream()
                    .map(tarefa -> {
                        TarefaDto tarefaDto = new TarefaDto();
                        tarefaDto.setTitulo(tarefa.getTitulo());
                        tarefaDto.setDescricao(tarefa.getDescricao());
                        tarefaDto.setPrioridade(tarefa.getPrioridade());
                        return tarefaDto;
                    })
                    .collect(Collectors.toList());

            usuarioDto.setTarefas(tarefas);
            return Optional.of(usuarioDto);
        }

        return Optional.empty();
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletar(String id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario adicionarTarefasAoUsuario(String usuarioId, List<String> tarefasIds) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            List<Tarefa> tarefas = tarefaRepository.findAllById(tarefasIds);
            usuario.setTarefas(tarefas);
            return usuarioRepository.save(usuario);
        }
        return null;
    }
}