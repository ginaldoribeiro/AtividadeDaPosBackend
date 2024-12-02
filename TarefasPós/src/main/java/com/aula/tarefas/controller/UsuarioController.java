package com.aula.tarefas.controller;

import com.aula.tarefas.dto.UsuarioDto;
import com.aula.tarefas.model.Usuario;
import com.aula.tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDto> listarTodos() {
        // Chama o serviço para obter todos os usuários com suas tarefas
        return usuarioService.listarTodos();
    }

    @PostMapping
    public Usuario criar(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setTelefone(usuarioDto.getTelefone());
        return usuarioService.salvar(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable String id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/tarefas")
    public Usuario adicionarTarefasAoUsuario(@PathVariable String id, @RequestBody List<String> tarefasIds) {
        return usuarioService.adicionarTarefasAoUsuario(id, tarefasIds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String id, @Valid @RequestBody UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id).map(dto -> {
            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setTelefone(usuarioDto.getTelefone());
            return usuarioService.salvar(usuario);
        });

        return usuarioOpt.isPresent() ? ResponseEntity.ok(usuarioOpt.get()) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        usuarioService.deletar(id);
    }
}
