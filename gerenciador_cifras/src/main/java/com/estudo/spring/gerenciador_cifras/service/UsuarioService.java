package com.estudo.spring.gerenciador_cifras.service;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.domain.Usuario;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import com.estudo.spring.gerenciador_cifras.domain.exception.ResourceNotFoundException;
import com.estudo.spring.gerenciador_cifras.dto.CriarUsuarioRequest;
import com.estudo.spring.gerenciador_cifras.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;

public class UsuarioService {
    private final UsuarioRepository usuarios;
    private final CifraService cifras;
    private final PlaylistService playlists;
    public UsuarioService(UsuarioRepository usuarios, CifraService cifras, PlaylistService playlists) { this.usuarios = usuarios; this.cifras = cifras; this.playlists = playlists; }
    public Usuario criar(CriarUsuarioRequest request) {
        requireRequest(request);
        requireNome(request.nome());
        if (usuarios.findByNome(request.nome()).isPresent()) throw new BusinessRuleException("nome de usuário já existe");
        return usuarios.save(Usuario.criar(UUID.randomUUID().toString(), request.nome(), request.descricao()));
    }
    public Usuario buscar(String id) { return usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário", id)); }
    public Usuario atualizarNome(String id, String nome) {
        Usuario usuario = buscar(id);
        requireNome(nome);
        usuarios.findByNome(nome).filter(outro -> !outro.getId().equals(id)).ifPresent(outro -> {
            throw new BusinessRuleException("nome de usuário já existe");
        });
        usuario.atualizarNome(nome);
        return usuarios.save(usuario);
    }
    public Usuario atualizarDescricao(String id, String descricao) { Usuario usuario = buscar(id); usuario.atualizarDescricao(descricao); return usuarios.save(usuario); }
    public List<Cifra> mostrarCifras(String id) { return cifras.listarDoUsuario(id); }
    public List<Playlist> mostrarPlaylists(String id) { return playlists.listarDoUsuario(id); }
    public List<Playlist> mostrarPlaylistsFavoritas(String id) { Usuario usuario = buscar(id); return usuario.getPlaylistsFavoritas().stream().map(this::buscarPlaylist).toList(); }
    public Usuario adicionarPlaylistFavorita(String id, String playlistId) { Usuario usuario = buscar(id); buscarPlaylist(playlistId); usuario.adicionarPlaylistFavorita(playlistId); return usuarios.save(usuario); }
    public Usuario removerPlaylistFavorita(String id, String playlistId) { Usuario usuario = buscar(id); usuario.removerPlaylistFavorita(playlistId); return usuarios.save(usuario); }
    public void remover(String id) {
        if (Usuario.ANONIMO_ID.equals(id)) throw new BusinessRuleException("usuário anônimo não pode ser removido");
        buscar(id);
        cifras.anonimizarDoUsuario(id);
        playlists.anonimizarDoUsuario(id);
        usuarios.deleteById(id);
    }
    private Playlist buscarPlaylist(String id) { return playlists.buscar(id); }
    private static void requireRequest(CriarUsuarioRequest request) {
        if (request == null) throw new BusinessRuleException("dados do usuário são obrigatórios");
    }
    private static void requireNome(String nome) {
        if (nome == null || nome.isBlank()) throw new BusinessRuleException("nome é obrigatório");
    }
}
