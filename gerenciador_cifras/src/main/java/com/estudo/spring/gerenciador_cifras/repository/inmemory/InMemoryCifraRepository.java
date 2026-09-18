package com.estudo.spring.gerenciador_cifras.repository.inmemory;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.repository.CifraRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCifraRepository implements CifraRepository {
    private final Map<String, Cifra> data = new ConcurrentHashMap<>();
    @Override public Cifra save(Cifra cifra) { data.put(cifra.getId(), cifra); return cifra; }
    @Override public Optional<Cifra> findById(String id) { return Optional.ofNullable(data.get(id)); }
    @Override public List<Cifra> findByUsuarioId(String usuarioId) { return data.values().stream().filter(c -> c.getUsuarioId().equals(usuarioId)).toList(); }
    @Override public void deleteById(String id) { data.remove(id); }
}
