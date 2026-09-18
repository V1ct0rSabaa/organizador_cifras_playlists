package com.estudo.spring.gerenciador_cifras.repository.mongo;

import com.estudo.spring.gerenciador_cifras.domain.enums.Dificuldade;
import com.estudo.spring.gerenciador_cifras.domain.enums.EstiloMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.InstrumentoMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.TomMusical;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cifras")
public record CifraDocument(
        @Id String id,
        String usuarioId,
        String autorMusica,
        InstrumentoMusical instrumentoMusical,
        EstiloMusical estiloMusical,
        TomMusical tom,
        Integer bpm,
        Dificuldade dificuldade,
        String conteudoCifra) {
}
