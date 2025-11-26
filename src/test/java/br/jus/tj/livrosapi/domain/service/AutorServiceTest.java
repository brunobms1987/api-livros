package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Autor;
import br.jus.tj.livrosapi.domain.repository.AutorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor autorExistente;
    private Autor autorNovo;

    @BeforeEach
    void setUp() {
        autorExistente = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .build();

        autorNovo = Autor.builder()
                .nome("Clarice Lispector")
                .build();
    }

    @Test
    void deveListarTodosOsAutores() {
        when(autorRepository.findAll()).thenReturn(List.of(autorExistente));

        List<Autor> resultado = autorService.listar();

        assertThat(resultado)
                .hasSize(1)
                .containsExactly(autorExistente);

        verify(autorRepository).findAll();
    }

    @Test
    void deveSalvarNovoAutor() {
        Autor autorSalvo = Autor.builder().id(10L).nome("Clarice Lispector").build();

        when(autorRepository.save(autorNovo)).thenReturn(autorSalvo);

        Autor resultado = autorService.salvar(autorNovo);

        assertThat(resultado.getId()).isEqualTo(10L);
        assertThat(resultado.getNome()).isEqualTo("Clarice Lispector");

        verify(autorRepository).save(autorNovo);
    }

    @Test
    void deveBuscarAutorPorIdQuandoExiste() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorExistente));

        Autor resultado = autorService.buscarPorId(1L);

        assertThat(resultado).isEqualTo(autorExistente);
        verify(autorRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> autorService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Autor não encontrado com ID: 99");
    }

    @Test
    void deveAtualizarAutorExistente() {
        Autor dadosAtualizacao = Autor.builder()
                .nome("Machado de Assis Atualizado")
                .build();

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorExistente));
        when(autorRepository.save(any(Autor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Autor resultado = autorService.atualizar(1L, dadosAtualizacao);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Machado de Assis Atualizado");

        verify(autorRepository).findById(1L);
        verify(autorRepository).save(autorExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorInexistente() {
        Autor dadosAtualizacao = Autor.builder().nome("Qualquer").build();

        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> autorService.atualizar(99L, dadosAtualizacao))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Autor não encontrado com ID: 99");
    }

    @Test
    void deveDeletarAutorExistente() {
        when(autorRepository.existsById(1L)).thenReturn(true);

        autorService.deletar(1L);

        verify(autorRepository).existsById(1L);
        verify(autorRepository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarAutorInexistente() {
        when(autorRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> autorService.deletar(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Autor não encontrado com ID: 99");

        verify(autorRepository).existsById(99L);
        verify(autorRepository, never()).deleteById(anyLong());
    }
}