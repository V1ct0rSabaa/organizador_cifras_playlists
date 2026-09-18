package com.estudo.spring.gerenciador_cifras.dto;

import com.estudo.spring.gerenciador_cifras.domain.enums.*;

public record CriarCifraRequest(String autorMusica, InstrumentoMusical instrumentoMusical, EstiloMusical estiloMusical,
                                TomMusical tom, Integer bpm, Dificuldade dificuldade, String conteudoCifra) {}
