package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    @Test
    void deveCriarUsuarioComDadosValidos() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", "Cantora");

        assertEquals("usuario-1", usuario.getId());
        assertEquals("Ana", usuario.getNome());
        assertEquals("Cantora", usuario.getDescricao());
        assertTrue(usuario.getPlaylistsFavoritas().isEmpty());
    }

    @Test
    void deveRejeitarNomeNuloOuEmBranco() {
        assertThrows(BusinessRuleException.class, () -> Usuario.criar("usuario-1", null, null));
        assertThrows(BusinessRuleException.class, () -> Usuario.criar("usuario-1", "   ", null));
    }

    @Test
    void deveRejeitarIdNuloOuEmBranco() {
        assertThrows(BusinessRuleException.class, () -> Usuario.criar(null, "Ana", null));
        assertThrows(BusinessRuleException.class, () -> Usuario.criar("   ", "Ana", null));
    }

    @Test
    void deveAtualizarNome() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);

        usuario.atualizarNome("Maria");

        assertEquals("Maria", usuario.getNome());
    }

    @Test
    void deveRejeitarAtualizacaoParaNomeNuloOuEmBranco() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);

        assertThrows(BusinessRuleException.class, () -> usuario.atualizarNome(null));
        assertThrows(BusinessRuleException.class, () -> usuario.atualizarNome("   "));
    }

    @Test
    void deveAtualizarEPermitirRemoverDescricao() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", "Descrição antiga");

        usuario.atualizarDescricao("Descrição nova");
        assertEquals("Descrição nova", usuario.getDescricao());

        usuario.atualizarDescricao(null);
        assertNull(usuario.getDescricao());
    }

    @Test
    void deveAdicionarPlaylistFavoritaSemDuplicar() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);

        usuario.adicionarPlaylistFavorita("playlist-1");
        usuario.adicionarPlaylistFavorita("playlist-1");

        assertEquals(List.of("playlist-1"), usuario.getPlaylistsFavoritas());
    }

    @Test
    void deveRejeitarPlaylistFavoritaSemId() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);

        assertThrows(BusinessRuleException.class, () -> usuario.adicionarPlaylistFavorita(null));
        assertThrows(BusinessRuleException.class, () -> usuario.adicionarPlaylistFavorita("   "));
    }

    @Test
    void deveRemoverPlaylistFavorita() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);
        usuario.adicionarPlaylistFavorita("playlist-1");

        usuario.removerPlaylistFavorita("playlist-1");

        assertTrue(usuario.getPlaylistsFavoritas().isEmpty());
    }

    @Test
    void deveCriarUsuarioAnonimoComIdFixo() {
        Usuario anonimo = Usuario.anonimo();

        assertEquals(Usuario.ANONIMO_ID, anonimo.getId());
        assertTrue(anonimo.getPlaylistsFavoritas().isEmpty());
    }

    @Test
    void deveExporListaDeFavoritosImutavel() {
        Usuario usuario = Usuario.criar("usuario-1", "Ana", null);
        usuario.adicionarPlaylistFavorita("playlist-1");

        assertThrows(UnsupportedOperationException.class, () -> usuario.getPlaylistsFavoritas().add("playlist-2"));
    }
}
