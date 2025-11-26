package br.jus.tj.livrosapi.domain.controller;

import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.service.AssuntoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService service;

    @GetMapping
    public ResponseEntity<List<Assunto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assunto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Assunto> salvar(@Valid @RequestBody Assunto assunto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(assunto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assunto> atualizar(@PathVariable Long id, @Valid @RequestBody Assunto assunto) {
        return ResponseEntity.ok(service.atualizar(id, assunto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}