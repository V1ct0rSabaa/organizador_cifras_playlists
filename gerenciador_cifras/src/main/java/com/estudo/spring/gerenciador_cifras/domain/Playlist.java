package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playlist {
    private final String id;
    private String usuarioId;
    private String descricao;
    private final List<String> cifras;

    public Playlist(String id, String usuarioId, String descricao, List<String> cifras) {
        this.id = required(id, "id");
        this.usuarioId = required(usuarioId, "usuarioId");
        this.descricao = blankToNull(descricao);
        this.cifras = new ArrayList<>(cifras == null ? List.of() : cifras);
        if (this.cifras.isEmpty()) throw new BusinessRuleException("playlist deve conter no mínimo uma cifra");
    }

    public void adicionarCifra(String cifraId) {
        if (cifraId == null || cifraId.isBlank()) throw new BusinessRuleException("cifraId é obrigatório");
        if (!cifras.contains(cifraId)) cifras.add(cifraId);
    }
    public void removerCifra(String cifraId) {
        if (cifras.size() == 1 && cifras.contains(cifraId)) throw new BusinessRuleException("playlist deve conter no mínimo uma cifra");
        cifras.remove(cifraId);
    }
    public void trocarPosicaoCifra(int origem, int destino) {
        if (origem < 0 || origem >= cifras.size() || destino < 0 || destino >= cifras.size()) throw new BusinessRuleException("posição de cifra inválida");
        String cifra = cifras.remove(origem);
        cifras.add(destino, cifra);
    }
    public void anonimizar() { this.usuarioId = Usuario.ANONIMO_ID; }
    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getDescricao() { return descricao; }
    public List<String> getCifras() { return List.copyOf(cifras); }
    private static String required(String value, String field) { if (value == null || value.isBlank()) throw new BusinessRuleException(field + " é obrigatório"); return value.trim(); }
    private static String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    @Override public boolean equals(Object other) { return other instanceof Playlist playlist && Objects.equals(id, playlist.id); }
    @Override public int hashCode() { return Objects.hash(id); }
}
