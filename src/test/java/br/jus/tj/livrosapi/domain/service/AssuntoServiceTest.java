package br.jus.tj.livrosapi.domain.service;

import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.repository.AssuntoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceTest {

    @Mock private AssuntoRepository repository;
    @InjectMocks private AssuntoService service;

    @Test
    void deveSalvarAssunto() {
        var assunto = Assunto.builder().descricao("Novo Assunto").build();
        var salvo = Assunto.builder().id(1L).descricao("Novo Assunto").build();

        when(repository.save(any())).thenReturn(salvo);

        var resultado = service.salvar(assunto);

        assertEquals(1L, resultado.getId());
        verify(repository).save(assunto);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () ->
                service.buscarPorId(99L));

        assertTrue(exception.getMessage().contains("não encontrado"));
    }
}