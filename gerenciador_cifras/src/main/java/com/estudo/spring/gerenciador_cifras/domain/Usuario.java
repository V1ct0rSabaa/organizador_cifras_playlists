package com.estudo.spring.gerenciador_cifras.domain;

import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    public static final String ANONIMO_ID = "00000000-0000-0000-0000-000000000000";

    private final String id;
    private String nome;
    private String descricao;
    private final List<String> playlistsFavoritas;

    public Usuario(String id, String nome, String descricao, List<String> playlistsFavoritas) {
        this.id = required(id, "id");
        this.nome = required(nome, "nome");
        this.descricao = blankToNull(descricao);
        this.playlistsFavoritas = new ArrayList<>(playlistsFavoritas == null ? List.of() : playlistsFavoritas);
    }

    public static Usuario criar(String id, String nome, String descricao) {
        return new Usuario(id, nome, descricao, List.of());
    }

    public static Usuario anonimo() {
        return new Usuario(ANONIMO_ID, "Usuário anônimo", "Usuário responsável por dados de contas removidas", List.of());
    }

    public void atualizarNome(String nome) { this.nome = required(nome, "nome"); }
    public void atualizarDescricao(String descricao) { this.descricao = blankToNull(descricao); }

    public void adicionarPlaylistFavorita(String playlistId) {
        if (playlistId == null || playlistId.isBlank()) throw new BusinessRuleException("playlistId é obrigatório");
        if (!playlistsFavoritas.contains(playlistId)) playlistsFavoritas.add(playlistId);
    }

    public void removerPlaylistFavorita(String playlistId) { playlistsFavoritas.remove(playlistId); }
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<String> getPlaylistsFavoritas() { return List.copyOf(playlistsFavoritas); }

    private static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new BusinessRuleException(field + " é obrigatório");
        return value.trim();
    }
    private static String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }

    @Override public boolean equals(Object other) { return other instanceof Usuario usuario && Objects.equals(id, usuario.id); }
    @Override public int hashCode() { return Objects.hash(id); }
}
