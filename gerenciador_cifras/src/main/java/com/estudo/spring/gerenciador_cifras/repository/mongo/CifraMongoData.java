package com.estudo.spring.gerenciador_cifras.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CifraMongoData extends MongoRepository<CifraDocument, String> {
    List<CifraDocument> findByUsuarioId(String usuarioId);
}
