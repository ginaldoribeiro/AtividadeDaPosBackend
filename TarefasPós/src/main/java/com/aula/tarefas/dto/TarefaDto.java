package com.aula.tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaDto {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String descricao;

    @NotBlank(message = "A prioridade é obrigatória")
    private String prioridade; // Valores esperados: baixa, media, alta

    private String usuarioId; // ID do usuário associado

    // Getters and Setters
}