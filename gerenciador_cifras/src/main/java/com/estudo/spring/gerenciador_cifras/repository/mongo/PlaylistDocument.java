package com.estudo.spring.gerenciador_cifras.repository.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document("playlists")
public record PlaylistDocument(
        @Id String id,
        String usuarioId,
        String descricao,
        List<String> cifras) {
}
