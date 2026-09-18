package com.estudo.spring.gerenciador_cifras.repository.mongo;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.repository.CifraRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongodb")
public class MongoCifraRepository implements CifraRepository {
    private final CifraMongoData data;
    public MongoCifraRepository(CifraMongoData data) { this.data = data; }
    @Override public Cifra save(Cifra cifra) { return toDomain(data.save(toDocument(cifra))); }
    @Override public Optional<Cifra> findById(String id) { return data.findById(id).map(this::toDomain); }
    @Override public List<Cifra> findByUsuarioId(String usuarioId) { return data.findByUsuarioId(usuarioId).stream().map(this::toDomain).toList(); }
    @Override public void deleteById(String id) { data.deleteById(id); }
    private CifraDocument toDocument(Cifra c) { return new CifraDocument(c.getId(), c.getUsuarioId(), c.getAutorMusica(), c.getInstrumentoMusical(), c.getEstiloMusical(), c.getTom(), c.getBpm(), c.getDificuldade(), c.getConteudoCifra()); }
    private Cifra toDomain(CifraDocument d) { return new Cifra(d.id(), d.usuarioId(), d.autorMusica(), d.instrumentoMusical(), d.estiloMusical(), d.tom(), d.bpm(), d.dificuldade(), d.conteudoCifra()); }
}
