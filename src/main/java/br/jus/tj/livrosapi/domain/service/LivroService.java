package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Livro;
import br.jus.tj.livrosapi.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final AutorService autorService;
    private final AssuntoService assuntoService;

    public List<Livro> listar() {
        return repository.findAll();
    }

    public Livro salvar(Livro livro) {
        validarRelacionamentos(livro);
        return repository.save(livro);
    }

    public Livro buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro existente = buscarPorId(id);

        existente.setTitulo(livroAtualizado.getTitulo());
        existente.setEditora(livroAtualizado.getEditora());
        existente.setEdicao(livroAtualizado.getEdicao());
        existente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        existente.setValor(livroAtualizado.getValor());

        existente.getAutores().clear();
        existente.getAutores().addAll(livroAtualizado.getAutores());

        existente.getAssuntos().clear();
        existente.getAssuntos().addAll(livroAtualizado.getAssuntos());

        validarRelacionamentos(existente);
        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private void validarRelacionamentos(Livro livro) {
        if (livro.getAutores() != null) {
            livro.getAutores().forEach(a -> autorService.buscarPorId(a.getId()));
        }
        if (livro.getAssuntos() != null) {
            livro.getAssuntos().forEach(a -> assuntoService.buscarPorId(a.getId()));
        }
    }
}