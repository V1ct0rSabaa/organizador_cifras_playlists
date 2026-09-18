package com.estudo.spring.gerenciador_cifras.service;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.domain.Usuario;
import com.estudo.spring.gerenciador_cifras.domain.enums.*;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import com.estudo.spring.gerenciador_cifras.domain.exception.ResourceNotFoundException;
import com.estudo.spring.gerenciador_cifras.dto.*;
import com.estudo.spring.gerenciador_cifras.repository.inmemory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationFlowTest {
    private InMemoryUsuarioRepository usuarios;
    private InMemoryCifraRepository cifras;
    private InMemoryPlaylistRepository playlists;
    private UsuarioService usuarioService;
    private CifraService cifraService;
    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        usuarios = new InMemoryUsuarioRepository();
        cifras = new InMemoryCifraRepository();
        playlists = new InMemoryPlaylistRepository();
        cifraService = new CifraService(cifras, usuarios);
        playlistService = new PlaylistService(playlists, cifras, usuarios);
        usuarioService = new UsuarioService(usuarios, cifraService, playlistService);
    }

    @Test
    void deveCriarUsuarioERejeitarNomeDuplicado() {
        Usuario primeiro = usuarioService.criar(new CriarUsuarioRequest("Ana", "Descrição"));

        assertNotNull(primeiro.getId());
        assertThrows(BusinessRuleException.class, () -> usuarioService.criar(new CriarUsuarioRequest("ana", null)));
    }

    @Test
    void deveCriarCifraEListarSomenteAsDoUsuario() {
        Usuario ana = criarUsuario("Ana");
        Usuario bruno = criarUsuario("Bruno");
        Cifra cifraAna = cifraService.criar(ana.getId(), cifraRequest("Ana"));
        cifraService.criar(bruno.getId(), cifraRequest("Bruno"));

        assertEquals(1, usuarioService.mostrarCifras(ana.getId()).size());
        assertEquals(cifraAna.getId(), usuarioService.mostrarCifras(ana.getId()).getFirst().getId());
    }

    @Test
    void deveImpedirQueOutroUsuarioAtualizeOuRemovaCifra() {
        Usuario ana = criarUsuario("Ana");
        Usuario bruno = criarUsuario("Bruno");
        Cifra cifra = cifraService.criar(ana.getId(), cifraRequest("Ana"));

        assertThrows(ResourceNotFoundException.class, () -> cifraService.atualizar(bruno.getId(), cifra.getId(), atualizarRequest()));
        assertThrows(ResourceNotFoundException.class, () -> cifraService.remover(bruno.getId(), cifra.getId()));
    }

    @Test
    void deveCriarPlaylistComCifrasDeUsuariosDiferentesEFavoritar() {
        Usuario ana = criarUsuario("Ana");
        Usuario bruno = criarUsuario("Bruno");
        Cifra cifraAna = cifraService.criar(ana.getId(), cifraRequest("Ana"));
        Cifra cifraBruno = cifraService.criar(bruno.getId(), cifraRequest("Bruno"));

        Playlist playlist = playlistService.criar(ana.getId(), new CriarPlaylistRequest("Misturada", java.util.List.of(cifraAna.getId(), cifraBruno.getId())));
        usuarioService.adicionarPlaylistFavorita(bruno.getId(), playlist.getId());

        assertEquals(2, playlist.getCifras().size());
        assertEquals(1, usuarioService.mostrarPlaylistsFavoritas(bruno.getId()).size());
    }

    @Test
    void deveRejeitarPlaylistSemCifrasOuComCifraInexistente() {
        Usuario ana = criarUsuario("Ana");

        assertThrows(BusinessRuleException.class, () -> playlistService.criar(ana.getId(), new CriarPlaylistRequest("Vazia", java.util.List.of())));
        assertThrows(ResourceNotFoundException.class, () -> playlistService.criar(ana.getId(), new CriarPlaylistRequest("Inválida", java.util.List.of("inexistente"))));
    }

    @Test
    void deveAnonimizarCifrasEPlaylistsAoRemoverUsuario() {
        Usuario ana = criarUsuario("Ana");
        Cifra cifra = cifraService.criar(ana.getId(), cifraRequest("Ana"));
        Playlist playlist = playlistService.criar(ana.getId(), new CriarPlaylistRequest("Playlist", java.util.List.of(cifra.getId())));

        usuarioService.remover(ana.getId());

        assertEquals(Usuario.ANONIMO_ID, cifraService.buscar(cifra.getId()).getUsuarioId());
        assertEquals(Usuario.ANONIMO_ID, playlistService.buscar(playlist.getId()).getUsuarioId());
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.buscar(ana.getId()));
        assertNotNull(usuarioService.buscar(Usuario.ANONIMO_ID));
    }

    @Test
    void deveAtualizarERemoverFavorito() {
        Usuario ana = criarUsuario("Ana");
        Cifra cifra = cifraService.criar(ana.getId(), cifraRequest("Ana"));
        Playlist playlist = playlistService.criar(ana.getId(), new CriarPlaylistRequest("Playlist", java.util.List.of(cifra.getId())));

        usuarioService.adicionarPlaylistFavorita(ana.getId(), playlist.getId());
        usuarioService.removerPlaylistFavorita(ana.getId(), playlist.getId());

        assertTrue(usuarioService.mostrarPlaylistsFavoritas(ana.getId()).isEmpty());
    }

    private Usuario criarUsuario(String nome) { return usuarioService.criar(new CriarUsuarioRequest(nome, null)); }

    private CriarCifraRequest cifraRequest(String autor) {
        return new CriarCifraRequest(autor, InstrumentoMusical.VIOLAO, EstiloMusical.ROCK, TomMusical.C, 120, Dificuldade.INICIANTE, "[C] Conteúdo");
    }

    private AtualizarCifraRequest atualizarRequest() {
        return new AtualizarCifraRequest("Novo autor", InstrumentoMusical.GUITARRA, EstiloMusical.POP, TomMusical.D, 90, Dificuldade.INTERMEDIARIO, "[D] Novo conteúdo");
    }
}
