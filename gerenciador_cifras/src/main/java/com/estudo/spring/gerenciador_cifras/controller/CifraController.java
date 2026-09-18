package com.estudo.spring.gerenciador_cifras.controller;

import com.estudo.spring.gerenciador_cifras.domain.Cifra;
import com.estudo.spring.gerenciador_cifras.dto.*;
import com.estudo.spring.gerenciador_cifras.service.CifraService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cifras")
public class CifraController {
    private final CifraService service;
    public CifraController(CifraService service) { this.service = service; }
    @PostMapping public ResponseEntity<Cifra> criar(@RequestHeader("X-Usuario-Id") String usuarioId, @RequestBody CriarCifraRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(usuarioId, request)); }
    @GetMapping("/{id}") public Cifra buscar(@PathVariable String id) { return service.buscar(id); }
    @PutMapping("/{id}") public Cifra atualizar(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id, @RequestBody AtualizarCifraRequest request) { return service.atualizar(usuarioId, id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void remover(@RequestHeader("X-Usuario-Id") String usuarioId, @PathVariable String id) { service.remover(usuarioId, id); }
    @GetMapping("/usuario/{usuarioId}") public List<Cifra> listar(@PathVariable String usuarioId) { return service.listarDoUsuario(usuarioId); }
}
