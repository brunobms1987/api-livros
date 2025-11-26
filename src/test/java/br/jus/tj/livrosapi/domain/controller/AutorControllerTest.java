package br.jus.tj.livrosapi.domain.controller;

import br.jus.tj.livrosapi.TestConfig;
import br.jus.tj.livrosapi.domain.model.Autor;
import br.jus.tj.livrosapi.domain.service.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AutorController.class)
@Import(TestConfig.class)
class AutorControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private AutorService autorService;

    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(autorService);
    }

    @Test
    void deveListarAutores() throws Exception {
        when(autorService.listar()).thenReturn(List.of(
                Autor.builder().id(1L).nome("Machado de Assis").build(),
                Autor.builder().id(2L).nome("Clarice Lispector").build()
        ));

        mockMvc.perform(get("/api/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Machado de Assis"))
                .andExpect(jsonPath("$[1].nome").value("Clarice Lispector"));
    }

    @Test
    void deveBuscarPorId() throws Exception {
        when(autorService.buscarPorId(3L))
                .thenReturn(Autor.builder().id(3L).nome("Jorge Amado").build());

        mockMvc.perform(get("/api/autores/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Jorge Amado"));
    }

    @Test
    void deveCriarAutor() throws Exception {
        Autor novo = Autor.builder().nome("Graciliano Ramos").build();
        Autor salvo = Autor.builder().id(10L).nome("Graciliano Ramos").build();

        when(autorService.salvar(any(Autor.class))).thenReturn(salvo);

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Graciliano Ramos"));
    }

    @Test
    void deveAtualizarAutor() throws Exception {
        Autor atualizado = Autor.builder().id(1L).nome("Machado de Assis - Atualizado").build();

        when(autorService.atualizar(eq(1L), any(Autor.class))).thenReturn(atualizado);

        mockMvc.perform(put("/api/autores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Machado de Assis - Atualizado"));
    }

    @Test
    void deveDeletarAutor() throws Exception {
        doNothing().when(autorService).deletar(5L);

        mockMvc.perform(delete("/api/autores/5"))
                .andExpect(status().isNoContent());

        verify(autorService, times(1)).deletar(5L);
    }
}