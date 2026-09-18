package com.estudo.spring.gerenciador_cifras.repository;

import com.estudo.spring.gerenciador_cifras.domain.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(String id);
    Optional<Usuario> findByNome(String nome);
    void deleteById(String id);
}
