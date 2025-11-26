package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.model.Autor;
import br.jus.tj.livrosapi.domain.model.Livro;
import br.jus.tj.livrosapi.domain.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock private LivroRepository livroRepository;
    @Mock private AutorService autorService;
    @Mock private AssuntoService assuntoService;

    @InjectMocks private LivroService livroService;

    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        autor = Autor.builder().id(1L).nome("Machado de Assis").build();
        assunto = Assunto.builder().id(10L).descricao("Romance").build();

        livro = Livro.builder()
                .id(100L)
                .titulo("Dom Casmurro")
                .editora("Garnier")
                .edicao(1)
                .anoPublicacao("1899")
                .valor(new BigDecimal("49.90"))
                .autores(Set.of(autor))
                .assuntos(Set.of(assunto))
                .build();
    }

    @Test
    void deveListarTodosOsLivros() {
        when(livroRepository.findAll()).thenReturn(List.of(livro));

        var resultado = livroService.listar();

        assertThat(resultado).hasSize(1).contains(livro);
        verify(livroRepository).findAll();
    }

    @Test
    void deveSalvarLivroComRelacionamentosValidos() {
        when(autorService.buscarPorId(1L)).thenReturn(autor);
        when(assuntoService.buscarPorId(10L)).thenReturn(assunto);
        when(livroRepository.save(livro)).thenReturn(livro);

        Livro salvo = livroService.salvar(livro);

        assertThat(salvo).isEqualTo(livro);
        verify(autorService).buscarPorId(1L);
        verify(assuntoService).buscarPorId(10L);
        verify(livroRepository).save(livro);
    }

    @Test
    void deveLancarExcecaoAoSalvarComAutorInexistente() {
        when(autorService.buscarPorId(999L)).thenThrow(new RuntimeException("Autor não encontrado"));

        Livro livroInvalido = Livro.builder()
                .titulo("Teste")
                .autores(Set.of(Autor.builder().id(999L).build()))
                .build();

        assertThatThrownBy(() -> livroService.salvar(livroInvalido))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Autor não encontrado");
    }

    @Test
    void deveBuscarLivroPorId() {
        when(livroRepository.findById(100L)).thenReturn(Optional.of(livro));

        Livro encontrado = livroService.buscarPorId(100L);

        assertThat(encontrado).isEqualTo(livro);
    }

    @Test
    void deveLancarExcecaoAoBuscarLivroInexistente() {
        when(livroRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livroService.buscarPorId(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Livro não encontrado com ID: 999");
    }

    @Test
    void deveAtualizarLivroMantendoRelacionamentos() {
        Livro dadosAtualizacao = Livro.builder()
                .titulo("Dom Casmurro - Nova Edição")
                .valor(new BigDecimal("79.90"))
                .autores(Set.of(autor))
                .assuntos(Set.of(assunto))
                .build();

        when(livroRepository.findById(100L)).thenReturn(Optional.of(livro));
        when(autorService.buscarPorId(1L)).thenReturn(autor);
        when(assuntoService.buscarPorId(10L)).thenReturn(assunto);
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        Livro atualizado = livroService.atualizar(100L, dadosAtualizacao);

        assertThat(atualizado.getTitulo()).isEqualTo("Dom Casmurro - Nova Edição");
        assertThat(atualizado.getValor()).isEqualTo(new BigDecimal("79.90"));
        assertThat(atualizado.getAutores()).containsExactly(autor);
    }

    @Test
    void deveDeletarLivroExistente() {
        when(livroRepository.existsById(100L)).thenReturn(true);

        livroService.deletar(100L);

        verify(livroRepository).deleteById(100L);
    }

    @Test
    void deveLancarExcecaoAoDeletarLivroInexistente() {
        when(livroRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> livroService.deletar(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Livro não encontrado com ID: 999");

        verify(livroRepository, never()).deleteById(anyLong());
    }
}