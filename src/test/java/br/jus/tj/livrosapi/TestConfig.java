package br.jus.tj.livrosapi;

import br.jus.tj.livrosapi.domain.service.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class TestConfig {

    @Bean
    public AssuntoService assuntoService() {
        return mock(AssuntoService.class);
    }

    @Bean
    public AutorService autorService() {
        return mock(AutorService.class);
    }

    @Bean
    public LivroService livroService() {
        return mock(LivroService.class);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(JdbcTemplate.class);
    }

    // Helper para criar mocks manualmente (usado em testes de service)
    private <T> T mock(Class<T> classToMock) {
        return org.mockito.Mockito.mock(classToMock);
    }
}