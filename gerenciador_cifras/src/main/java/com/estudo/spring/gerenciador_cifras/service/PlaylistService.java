package com.estudo.spring.gerenciador_cifras.service;

import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import com.estudo.spring.gerenciador_cifras.domain.exception.ResourceNotFoundException;
import com.estudo.spring.gerenciador_cifras.dto.CriarPlaylistRequest;
import com.estudo.spring.gerenciador_cifras.repository.CifraRepository;
import com.estudo.spring.gerenciador_cifras.repository.PlaylistRepository;
import com.estudo.spring.gerenciador_cifras.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;

public class PlaylistService {
    private final PlaylistRepository playlists;
    private final CifraRepository cifras;
    private final UsuarioRepository usuarios;
    public PlaylistService(PlaylistRepository playlists, CifraRepository cifras, UsuarioRepository usuarios) { this.playlists = playlists; this.cifras = cifras; this.usuarios = usuarios; }
    public Playlist criar(String usuarioId, CriarPlaylistRequest request) {
        if (request == null) throw new BusinessRuleException("dados da playlist são obrigatórios");
        requireUsuario(usuarioId);
        List<String> ids = request.cifras() == null ? List.of() : request.cifras();
        ids.forEach(id -> cifras.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cifra", id)));
        return playlists.save(new Playlist(UUID.randomUUID().toString(), usuarioId, request.descricao(), ids));
    }
    public void remover(String usuarioId, String playlistId) { playlists.deleteById(own(playlistId, usuarioId).getId()); }
    public Playlist buscar(String playlistId) { return playlists.findById(playlistId).orElseThrow(() -> new ResourceNotFoundException("Playlist", playlistId)); }
    public Playlist adicionarCifra(String usuarioId, String playlistId, String cifraId) { Playlist p = own(playlistId, usuarioId); requireCifra(cifraId); p.adicionarCifra(cifraId); return playlists.save(p); }
    public Playlist removerCifra(String usuarioId, String playlistId, String cifraId) { Playlist p = own(playlistId, usuarioId); p.removerCifra(cifraId); return playlists.save(p); }
    public Playlist trocarPosicaoCifra(String usuarioId, String playlistId, int origem, int destino) { Playlist p = own(playlistId, usuarioId); p.trocarPosicaoCifra(origem, destino); return playlists.save(p); }
    public List<Playlist> listarDoUsuario(String usuarioId) { requireUsuario(usuarioId); return playlists.findByUsuarioId(usuarioId); }
    public void anonimizarDoUsuario(String usuarioId) { playlists.findByUsuarioId(usuarioId).forEach(p -> { p.anonimizar(); playlists.save(p); }); }
    private Playlist own(String id, String usuarioId) { Playlist p = playlists.findById(id).orElseThrow(() -> new ResourceNotFoundException("Playlist", id)); if (!p.getUsuarioId().equals(usuarioId)) throw new ResourceNotFoundException("Playlist", id); return p; }
    private void requireUsuario(String id) { usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário", id)); }
    private void requireCifra(String id) { cifras.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cifra", id)); }
}
