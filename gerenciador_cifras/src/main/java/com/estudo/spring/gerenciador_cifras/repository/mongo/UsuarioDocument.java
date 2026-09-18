package com.estudo.spring.gerenciador_cifras.repository.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document("usuarios")
public record UsuarioDocument(@Id String id, String nome, String descricao, List<String> playlistsFavoritas) {}
