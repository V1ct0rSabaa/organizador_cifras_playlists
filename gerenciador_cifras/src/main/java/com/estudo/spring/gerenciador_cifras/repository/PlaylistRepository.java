package com.estudo.spring.gerenciador_cifras.repository;

import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {
    Playlist save(Playlist playlist);
    Optional<Playlist> findById(String id);
    List<Playlist> findByUsuarioId(String usuarioId);
    void deleteById(String id);
}
