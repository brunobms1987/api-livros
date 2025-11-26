package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Autor;
import br.jus.tj.livrosapi.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;

    public List<Autor> listar() {
        return repository.findAll();
    }

    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

    public Autor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));
    }

    public Autor atualizar(Long id, Autor autorAtualizado) {
        Autor existente = buscarPorId(id);
        existente.setNome(autorAtualizado.getNome());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Autor não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}