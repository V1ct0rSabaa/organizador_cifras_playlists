package com.estudo.spring.gerenciador_cifras.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PlaylistMongoData extends MongoRepository<PlaylistDocument, String> {
    List<PlaylistDocument> findByUsuarioId(String usuarioId);
}
