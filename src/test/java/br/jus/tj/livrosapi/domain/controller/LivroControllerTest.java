package br.jus.tj.livrosapi.domain.controller;

import br.jus.tj.livrosapi.TestConfig;
import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.model.Autor;
import br.jus.tj.livrosapi.domain.model.Livro;
import br.jus.tj.livrosapi.domain.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroController.class)
@Import(TestConfig.class)
class LivroControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private LivroService livroService; // mockado via TestConfig

    @Autowired private ObjectMapper objectMapper;

    private Livro livro;

    @BeforeEach
    void setUp() {
        reset(livroService);

        Autor autor = Autor.builder().id(1L).nome("Machado de Assis").build();
        Assunto assunto = Assunto.builder().id(10L).descricao("Romance").build();

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
    void deveListarLivros() throws Exception {
        when(livroService.listar()).thenReturn(List.of(livro));

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Dom Casmurro"))
                .andExpect(jsonPath("$[0].autores[0].nome").value("Machado de Assis"));
    }

    @Test
    void deveBuscarLivroPorId() throws Exception {
        when(livroService.buscarPorId(100L)).thenReturn(livro);

        mockMvc.perform(get("/api/livros/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro"))
                .andExpect(jsonPath("$.valor").value(49.90));
    }

    @Test
    void deveCriarLivro() throws Exception {
        Livro novoLivro = Livro.builder()
                .titulo("Memórias Póstumas")
                .editora("Editora X")
                .edicao(2)
                .anoPublicacao("1881")
                .valor(new BigDecimal("59.90"))
                .autores(Set.of(Autor.builder().id(1L).build()))
                .assuntos(Set.of(Assunto.builder().id(10L).build()))
                .build();

        Livro salvo = Livro.builder()
                .id(200L)
                .titulo("Memórias Póstumas")
                .editora("Editora X")
                .edicao(2)
                .anoPublicacao("1881")
                .valor(new BigDecimal("59.90"))
                .autores(Set.of(Autor.builder().id(1L).nome("Machado de Assis").build()))
                .assuntos(Set.of(Assunto.builder().id(10L).descricao("Romance").build()))
                .build();

        when(livroService.salvar(any(Livro.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoLivro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(200))
                .andExpect(jsonPath("$.titulo").value("Memórias Póstumas"));
    }

    @Test
    void deveAtualizarLivro() throws Exception {
        Livro atualizado = Livro.builder()
                .titulo("Dom Casmurro - Edição Especial")
                .valor(new BigDecimal("89.90"))
                .build();

        Livro resultado = Livro.builder()
                .id(100L)
                .titulo("Dom Casmurro - Edição Especial")
                .editora("Garnier")
                .edicao(1)
                .anoPublicacao("1899")
                .valor(new BigDecimal("89.90"))
                .autores(livro.getAutores())
                .assuntos(livro.getAssuntos())
                .build();

        when(livroService.atualizar(eq(100L), any(Livro.class))).thenReturn(resultado);

        mockMvc.perform(put("/api/livros/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Dom Casmurro - Edição Especial"))
                .andExpect(jsonPath("$.valor").value(89.90));
    }

    @Test
    void deveDeletarLivro() throws Exception {
        doNothing().when(livroService).deletar(100L);

        mockMvc.perform(delete("/api/livros/100"))
                .andExpect(status().isNoContent());

        verify(livroService).deletar(100L);
    }
}