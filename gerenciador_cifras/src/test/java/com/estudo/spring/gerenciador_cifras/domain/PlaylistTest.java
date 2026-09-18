package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    @Test
    // highlander
    void deveExigirAoMenosUmaCifra() {
        assertThrows(BusinessRuleException.class, () -> new Playlist("playlist-1", "usuario-1", "Favoritas", List.of()));
    }

    @Test
    void deveAdicionarRemoverETrocarPosicaoDeCifras() {
        Playlist playlist = new Playlist("playlist-1", "usuario-1", "Favoritas", List.of("cifra-1"));

        playlist.adicionarCifra("cifra-2");
        playlist.trocarPosicaoCifra(0, 1);
        playlist.removerCifra("cifra-1");

        assertEquals(List.of("cifra-2"), playlist.getCifras());
    }

    @Test
    void deveRejeitarRemocaoDaUltimaCifra() {
        Playlist playlist = new Playlist("playlist-1", "usuario-1", null, List.of("cifra-1"));

        assertThrows(BusinessRuleException.class, () -> playlist.removerCifra("cifra-1"));
    }

    @Test
    void deveRejeitarPosicaoInvalida() {
        Playlist playlist = new Playlist("playlist-1", "usuario-1", null, List.of("cifra-1"));

        assertThrows(BusinessRuleException.class, () -> playlist.trocarPosicaoCifra(0, 1));
        assertThrows(BusinessRuleException.class, () -> playlist.trocarPosicaoCifra(-1, 0));
    }

    @Test
    void deveAnonimizarPlaylistSemTrocarSeuProprietarioPorOutroUsuario() {
        Playlist playlist = new Playlist("playlist-1", "usuario-1", null, List.of("cifra-1"));

        playlist.anonimizar();

        assertEquals(Usuario.ANONIMO_ID, playlist.getUsuarioId());
    }
}
