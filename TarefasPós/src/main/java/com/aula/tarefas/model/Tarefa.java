package com.aula.tarefas.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tarefas")
public class Tarefa {

    @Id
    private String id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String descricao;

    @NotBlank(message = "A prioridade é obrigatória")
    private String prioridade; // baixa, media, alta

    private LocalDateTime dataCriacao;

    private boolean deletado = false;

    @DBRef
    private Usuario usuario; // Usuário associado à tarefa

    // Getters and Setters
}
