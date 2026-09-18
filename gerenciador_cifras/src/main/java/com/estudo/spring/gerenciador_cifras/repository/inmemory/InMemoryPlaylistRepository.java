package com.estudo.spring.gerenciador_cifras.repository.inmemory;

import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.repository.PlaylistRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPlaylistRepository implements PlaylistRepository {
    private final Map<String, Playlist> data = new ConcurrentHashMap<>();
    @Override public Playlist save(Playlist playlist) { data.put(playlist.getId(), playlist); return playlist; }
    @Override public Optional<Playlist> findById(String id) { return Optional.ofNullable(data.get(id)); }
    @Override public List<Playlist> findByUsuarioId(String usuarioId) { return data.values().stream().filter(p -> p.getUsuarioId().equals(usuarioId)).toList(); }
    @Override public void deleteById(String id) { data.remove(id); }
}
