package com.estudo.spring.gerenciador_cifras.controller;

import com.estudo.spring.gerenciador_cifras.domain.Playlist;
import com.estudo.spring.gerenciador_cifras.dto.CriarPlaylistRequest;
import com.estudo.spring.gerenciador_cifras.service.PlaylistService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService service;
    public PlaylistController(PlaylistService service) { this.service = service; }
    @PostMapping public ResponseEntity<Playlist> criar(@RequestHeader("X-Usuario-Id") String usuarioId, @RequestBody CriarPlaylistRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(usuarioId, request)); }
    @GetMapping("/usuario/{usuarioId}") public List<Playlist> listar(@PathVariable String usuarioId) { return service.listarDoUsuario(usuarioId); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id) { service.remover(usuarioId, id); }
    @PostMapping("/{id}/cifras/{cifraId}") public Playlist adicionar(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id, @PathVariable String cifraId) { return service.adicionarCifra(usuarioId, id, cifraId); }
    @DeleteMapping("/{id}/cifras/{cifraId}") public Playlist removerCifra(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id, @PathVariable String cifraId) { return service.removerCifra(usuarioId, id, cifraId); }
    @PatchMapping("/{id}/cifras/posicao") public Playlist trocarPosicao(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id, @RequestParam int origem, @RequestParam int destino) { return service.trocarPosicaoCifra(usuarioId, id, origem, destino); }
}
