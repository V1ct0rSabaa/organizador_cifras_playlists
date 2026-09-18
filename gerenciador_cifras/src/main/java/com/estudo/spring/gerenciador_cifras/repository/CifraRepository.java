package com.estudo.spring.gerenciador_cifras.repository;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import java.util.List;
import java.util.Optional;

public interface CifraRepository {
    Cifra save(Cifra cifra);
    Optional<Cifra> findById(String id);
    List<Cifra> findByUsuarioId(String usuarioId);
    void deleteById(String id);
}
