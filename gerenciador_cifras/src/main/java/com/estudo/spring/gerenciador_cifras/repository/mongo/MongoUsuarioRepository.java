package com.estudo.spring.gerenciador_cifras.repository.mongo;

import com.estudo.spring.gerenciador_cifras.domain.Usuario;
import com.estudo.spring.gerenciador_cifras.repository.UsuarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Profile("mongodb")
public class MongoUsuarioRepository implements UsuarioRepository {
    private final UsuarioMongoData data;
    public MongoUsuarioRepository(UsuarioMongoData data) { this.data = data; }
    @Override public Usuario save(Usuario usuario) { return toDomain(data.save(toDocument(usuario))); }
    @Override public Optional<Usuario> findById(String id) { return data.findById(id).map(this::toDomain); }
    @Override public Optional<Usuario> findByNome(String nome) { return data.findByNomeIgnoreCase(nome).map(this::toDomain); }
    @Override public void deleteById(String id) { data.deleteById(id); }
    private UsuarioDocument toDocument(Usuario u) { return new UsuarioDocument(u.getId(), u.getNome(), u.getDescricao(), u.getPlaylistsFavoritas()); }
    private Usuario toDomain(UsuarioDocument d) { return new Usuario(d.id(), d.nome(), d.descricao(), d.playlistsFavoritas()); }
}
