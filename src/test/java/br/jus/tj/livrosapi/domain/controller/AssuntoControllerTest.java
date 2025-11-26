package br.jus.tj.livrosapi.domain.controller;

import br.jus.tj.livrosapi.TestConfig;
import br.jus.tj.livrosapi.domain.model.Assunto;
import br.jus.tj.livrosapi.domain.service.AssuntoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssuntoController.class)
@Import(TestConfig.class)
class AssuntoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private AssuntoService assuntoService;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(assuntoService);
    }

    @Test
    void deveListarAssuntos() throws Exception {
        when(assuntoService.listar()).thenReturn(List.of(
                Assunto.builder().id(1L).descricao("Tecnologia").build(),
                Assunto.builder().id(2L).descricao("Direito").build()
        ));

        mockMvc.perform(get("/api/assuntos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].descricao").value("Direito"));
    }

    @Test
    void deveBuscarPorId() throws Exception {
        when(assuntoService.buscarPorId(5L))
                .thenReturn(Assunto.builder().id(5L).descricao("História").build());

        mockMvc.perform(get("/api/assuntos/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("História"));
    }

    @Test
    void deveCriarAssunto() throws Exception {
        var novo = Assunto.builder().descricao("Filosofia").build();
        var salvo = Assunto.builder().id(10L).descricao("Filosofia").build();

        when(assuntoService.salvar(any())).thenReturn(salvo);

        mockMvc.perform(post("/api/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void deveAtualizarAssunto() throws Exception {
        var atualizado = Assunto.builder().id(1L).descricao("Tecnologia Atualizada").build();
        when(assuntoService.atualizar(eq(1L), any())).thenReturn(atualizado);

        mockMvc.perform(put("/api/assuntos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Tecnologia Atualizada"));
    }

    @Test
    void deveDeletarAssunto() throws Exception {
        doNothing().when(assuntoService).deletar(1L);

        mockMvc.perform(delete("/api/assuntos/1"))
                .andExpect(status().isNoContent());

        verify(assuntoService).deletar(1L);
    }
}