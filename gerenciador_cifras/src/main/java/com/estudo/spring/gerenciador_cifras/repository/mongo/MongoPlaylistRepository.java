package com.estudo.spring.gerenciador_cifras.repository.mongo;

import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.repository.PlaylistRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongodb")
public class MongoPlaylistRepository implements PlaylistRepository {
    private final PlaylistMongoData data;
    public MongoPlaylistRepository(PlaylistMongoData data) { this.data = data; }
    @Override public Playlist save(Playlist playlist) { return toDomain(data.save(toDocument(playlist))); }
    @Override public Optional<Playlist> findById(String id) { return data.findById(id).map(this::toDomain); }
    @Override public List<Playlist> findByUsuarioId(String usuarioId) { return data.findByUsuarioId(usuarioId).stream().map(this::toDomain).toList(); }
    @Override public void deleteById(String id) { data.deleteById(id); }
    private PlaylistDocument toDocument(Playlist p) { return new PlaylistDocument(p.getId(), p.getUsuarioId(), p.getDescricao(), p.getCifras()); }
    private Playlist toDomain(PlaylistDocument d) { return new Playlist(d.id(), d.usuarioId(), d.descricao(), d.cifras()); }
}
