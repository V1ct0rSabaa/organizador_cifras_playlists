package com.estudo.spring.gerenciador_cifras.service;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.domain.exception.BusinessRuleException;
import com.estudo.spring.gerenciador_cifras.domain.exception.ResourceNotFoundException;
import com.estudo.spring.gerenciador_cifras.dto.AtualizarCifraRequest;
import com.estudo.spring.gerenciador_cifras.dto.CriarCifraRequest;
import com.estudo.spring.gerenciador_cifras.repository.CifraRepository;
import com.estudo.spring.gerenciador_cifras.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;

public class CifraService {
    private final CifraRepository cifras;
    private final UsuarioRepository usuarios;
    public CifraService(CifraRepository cifras, UsuarioRepository usuarios) { this.cifras = cifras; this.usuarios = usuarios; }
    public Cifra criar(String usuarioId, CriarCifraRequest request) {
        requireRequest(request);
        requireUsuario(usuarioId);
        return cifras.save(new Cifra(UUID.randomUUID().toString(), usuarioId, request.autorMusica(), request.instrumentoMusical(), request.estiloMusical(), request.tom(), request.bpm(), request.dificuldade(), request.conteudoCifra()));
    }
    public Cifra atualizar(String usuarioId, String cifraId, AtualizarCifraRequest request) {
        requireRequest(request);
        Cifra cifra = own(cifraId, usuarioId);
        cifra.atualizar(request.autorMusica(), request.instrumentoMusical(), request.estiloMusical(), request.tom(), request.bpm(), request.dificuldade(), request.conteudoCifra());
        return cifras.save(cifra);
    }
    public void remover(String usuarioId, String cifraId) { cifras.deleteById(own(cifraId, usuarioId).getId()); }
    public Cifra buscar(String cifraId) { return cifras.findById(cifraId).orElseThrow(() -> new ResourceNotFoundException("Cifra", cifraId)); }
    public List<Cifra> listarDoUsuario(String usuarioId) { requireUsuario(usuarioId); return cifras.findByUsuarioId(usuarioId); }
    public void anonimizarDoUsuario(String usuarioId) { cifras.findByUsuarioId(usuarioId).forEach(c -> { c.anonimizar(); cifras.save(c); }); }
    private Cifra own(String id, String usuarioId) { Cifra cifra = buscar(id); if (!cifra.getUsuarioId().equals(usuarioId)) throw new ResourceNotFoundException("Cifra", id); return cifra; }
    private void requireUsuario(String id) { usuarios.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário", id)); }
    private static void requireRequest(Object request) { if (request == null) throw new BusinessRuleException("dados da cifra são obrigatórios"); }
}
