package br.jus.tj.livrosapi.domain.controller;

import br.jus.tj.livrosapi.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RelatorioController.class)
@Import(TestConfig.class)
class RelatorioControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void deveGerarPdfRelatorio() throws Exception {
        mockMvc.perform(get("/api/relatorio/livros-por-autor"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION,
                        org.hamcrest.Matchers.containsString("livros_por_autor.pdf")))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }
}