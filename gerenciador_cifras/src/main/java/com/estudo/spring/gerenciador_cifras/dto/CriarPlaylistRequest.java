package com.estudo.spring.gerenciador_cifras.dto;

import java.util.List;

public record CriarPlaylistRequest(String descricao, List<String> cifras) {}
