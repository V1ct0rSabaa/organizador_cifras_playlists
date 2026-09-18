package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.enums.Dificuldade;
import com.estudo.spring.gerenciador_cifras.domain.enums.EstiloMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.InstrumentoMusical;
import com.estudo.spring.gerenciador_cifras.domain.enums.TomMusical;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import java.util.Objects;

public class Cifra {
    private final String id;
    private String usuarioId;
    private String autorMusica;
    private InstrumentoMusical instrumentoMusical;
    private EstiloMusical estiloMusical;
    private TomMusical tom;
    private Integer bpm;
    private Dificuldade dificuldade;
    private String conteudoCifra;

    public Cifra(String id, String usuarioId, String autorMusica, InstrumentoMusical instrumentoMusical,
                 EstiloMusical estiloMusical, TomMusical tom, Integer bpm, Dificuldade dificuldade, String conteudoCifra) {
        this.id = required(id, "id");
        this.usuarioId = required(usuarioId, "usuarioId");
        this.autorMusica = blankToNull(autorMusica);
        this.instrumentoMusical = required(instrumentoMusical, "instrumentoMusical");
        this.estiloMusical = required(estiloMusical, "estiloMusical");
        this.tom = required(tom, "key/tom");
        this.dificuldade = required(dificuldade, "dificuldade");
        this.conteudoCifra = required(conteudoCifra, "conteudoCifra");
        validarBpm(bpm);
        this.bpm = bpm;
    }

    public void atualizar(String autorMusica, InstrumentoMusical instrumentoMusical, EstiloMusical estiloMusical,
                          TomMusical tom, Integer bpm, Dificuldade dificuldade, String conteudoCifra) {
        this.autorMusica = blankToNull(autorMusica);
        this.instrumentoMusical = required(instrumentoMusical, "instrumentoMusical");
        this.estiloMusical = required(estiloMusical, "estiloMusical");
        this.tom = required(tom, "key/tom");
        validarBpm(bpm);
        this.bpm = bpm;
        this.dificuldade = required(dificuldade, "dificuldade");
        this.conteudoCifra = required(conteudoCifra, "conteudoCifra");
    }

    public void anonimizar() { this.usuarioId = Usuario.ANONIMO_ID; }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getAutorMusica() { return autorMusica; }
    public InstrumentoMusical getInstrumentoMusical() { return instrumentoMusical; }
    public EstiloMusical getEstiloMusical() { return estiloMusical; }
    public TomMusical getTom() { return tom; }
    public Integer getBpm() { return bpm; }
    public Dificuldade getDificuldade() { return dificuldade; }
    public String getConteudoCifra() { return conteudoCifra; }

    private static void validarBpm(Integer bpm) {
        if (bpm != null && (bpm < 30 || bpm > 300)) {
            throw new BusinessRuleException("bpm deve estar entre 30 e 300");
        }
    }
    private static String required(String value, String field) { if (value == null || value.isBlank()) throw new BusinessRuleException(field + " é obrigatório"); return value.trim(); }
    private static <T> T required(T value, String field) { if (value == null) throw new BusinessRuleException(field + " é obrigatório"); return value; }
    private static String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    @Override public boolean equals(Object other) { return other instanceof Cifra cifra && Objects.equals(id, cifra.id); }
    @Override public int hashCode() { return Objects.hash(id); }
}
