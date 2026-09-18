package com.estudo.spring.gerenciador_cifras.controller;

import com.estudo.spring.gerenciador_cifras.domain.*;
import com.estudo.spring.gerenciador_cifras.dto.CriarUsuarioRequest;
import com.estudo.spring.gerenciador_cifras.service.UsuarioService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service; }
    @PostMapping public ResponseEntity<Usuario> criar(@RequestBody CriarUsuarioRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request)); }
    @GetMapping("/{id}") public Usuario buscar(@PathVariable String id) { return service.buscar(id); }
    @PatchMapping("/{id}/nome") public Usuario atualizarNome(@PathVariable String id, @RequestBody String nome) { return service.atualizarNome(id, nome); }
    @PatchMapping("/{id}/descricao") public Usuario atualizarDescricao(@PathVariable String id, @RequestBody String descricao) { return service.atualizarDescricao(id, descricao); }
    @GetMapping("/{id}/cifras") public Object cifras(@PathVariable String id) { return service.mostrarCifras(id); }
    @GetMapping("/{id}/playlists") public Object playlists(@PathVariable String id) { return service.mostrarPlaylists(id); }
    @GetMapping("/{id}/playlists-favoritas") public Object favoritas(@PathVariable String id) { return service.mostrarPlaylistsFavoritas(id); }
    @PutMapping("/{id}/playlists-favoritas/{playlistId}") public Usuario favoritar(@PathVariable String id, @PathVariable String playlistId) { return service.adicionarPlaylistFavorita(id, playlistId); }
    @DeleteMapping("/{id}/playlists-favoritas/{playlistId}") public Usuario desfavoritar(@PathVariable String id, @PathVariable String playlistId) { return service.removerPlaylistFavorita(id, playlistId); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@PathVariable String id) { service.remover(id); }
}
