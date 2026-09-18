package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.enums.Dificuldade;
import com.estudo.spring.gerenciador_cifras.domain.enums.EstiloMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.InstrumentoMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.TomMusical;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CifraTest {
    @Test
    void deveCriarCifraComDadosValidos() {
        Cifra cifra = criarCifra(120);

        assertEquals("usuario-1", cifra.getUsuarioId());
        assertEquals(120, cifra.getBpm());
        assertEquals("[C] Conteúdo", cifra.getConteudoCifra());
    }

    @Test
    void devePermitirBpmNulo() {
        assertDoesNotThrow(() -> criarCifra(null));
    }

    @Test
    void deveAceitarLimitesDoIntervaloDeBpm() {
        assertDoesNotThrow(() -> criarCifra(30));
        assertDoesNotThrow(() -> criarCifra(300));
    }

    @Test
    void deveRejeitarBpmForaDoIntervalo() {
        assertThrows(BusinessRuleException.class, () -> criarCifra(29));
        assertThrows(BusinessRuleException.class, () -> criarCifra(301));
    }

    @Test
    void deveRejeitarCamposObrigatoriosAusentes() {
        assertThrows(BusinessRuleException.class, () -> new Cifra("id", "usuario", null, null, EstiloMusical.ROCK, TomMusical.C, 120, Dificuldade.INICIANTE, "conteudo"));
        assertThrows(BusinessRuleException.class, () -> new Cifra("id", "usuario", null, InstrumentoMusical.VIOLAO, null, TomMusical.C, 120, Dificuldade.INICIANTE, "conteudo"));
        assertThrows(BusinessRuleException.class, () -> new Cifra("id", "usuario", null, InstrumentoMusical.VIOLAO, EstiloMusical.ROCK, null, 120, Dificuldade.INICIANTE, "conteudo"));
        assertThrows(BusinessRuleException.class, () -> new Cifra("id", "usuario", null, InstrumentoMusical.VIOLAO, EstiloMusical.ROCK, TomMusical.C, 120, null, "conteudo"));
        assertThrows(BusinessRuleException.class, () -> new Cifra("id", "usuario", null, InstrumentoMusical.VIOLAO, EstiloMusical.ROCK, TomMusical.C, 120, Dificuldade.INICIANTE, null));
    }

    @Test
    void deveAtualizarDadosSemAlterarProprietario() {
        Cifra cifra = criarCifra(120);

        cifra.atualizar("Novo autor", InstrumentoMusical.GUITARRA, EstiloMusical.POP, TomMusical.D, 90, Dificuldade.INTERMEDIARIO, "Novo conteúdo");

        assertEquals("usuario-1", cifra.getUsuarioId());
        assertEquals(InstrumentoMusical.GUITARRA, cifra.getInstrumentoMusical());
        assertEquals(90, cifra.getBpm());
    }

    @Test
    void deveAnonimizarCifra() {
        Cifra cifra = criarCifra(120);

        cifra.anonimizar();

        assertEquals(Usuario.ANONIMO_ID, cifra.getUsuarioId());
    }

    private static Cifra criarCifra(Integer bpm) {
        return new Cifra("cifra-1", "usuario-1", "Autor", InstrumentoMusical.VIOLAO, EstiloMusical.ROCK, TomMusical.C, bpm, Dificuldade.INICIANTE, "[C] Conteúdo");
    }
}
