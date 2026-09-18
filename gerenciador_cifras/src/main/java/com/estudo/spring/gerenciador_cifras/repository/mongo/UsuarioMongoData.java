package com.estudo.spring.gerenciador_cifras.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioMongoData extends MongoRepository<UsuarioDocument, String> {
    Optional<UsuarioDocument> findByNomeIgnoreCase(String nome);
}
