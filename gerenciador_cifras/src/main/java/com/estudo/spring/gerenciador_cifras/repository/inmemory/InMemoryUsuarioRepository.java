package com.estudo.spring.gerenciador_cifras.repository.inmemory;

import com.estudo.spring.gerenciador_cifras.domain.Usuario;
import com.estudo.spring.gerenciador_cifras.repository.UsuarioRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUsuarioRepository implements UsuarioRepository {
    private final Map<String, Usuario> data = new ConcurrentHashMap<>();
    public InMemoryUsuarioRepository() { save(Usuario.anonimo()); }
    @Override public Usuario save(Usuario usuario) { data.put(usuario.getId(), usuario); return usuario; }
    @Override public Optional<Usuario> findById(String id) { return Optional.ofNullable(data.get(id)); }
    @Override public Optional<Usuario> findByNome(String nome) { return data.values().stream().filter(u -> u.getNome().equalsIgnoreCase(nome)).findFirst(); }
    @Override public void deleteById(String id) { data.remove(id); }
}
