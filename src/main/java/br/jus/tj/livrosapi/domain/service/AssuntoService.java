package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository repository;

    public List<Assunto> listar() {
        return repository.findAll();
    }

    public Assunto salvar(Assunto assunto) {
        return repository.save(assunto);
    }

    public Assunto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assunto não encontrado com ID: " + id));
    }

    public Assunto atualizar(Long id, Assunto assuntoAtualizado) {
        Assunto existente = buscarPorId(id);
        existente.setDescricao(assuntoAtualizado.getDescricao());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Assunto não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}