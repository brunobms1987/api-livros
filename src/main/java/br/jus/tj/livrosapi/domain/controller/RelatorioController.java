package br.jus.tj.livrosapi.domain.controller;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/api/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/livros-por-autor")
    public ResponseEntity<byte[]> gerarRelatorio() {
        InputStream reportStream = null;
        try {
            reportStream = RelatorioController.class
                    .getClassLoader()
                    .getResourceAsStream("reports/relatorio_livros_por_autor.jrxml");

            if (reportStream == null) {
                System.err.println("ERRO: Arquivo não encontrado com classloader!");
                return ResponseEntity.status(404)
                        .body("Relatório não encontrado!".getBytes());
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    new HashMap<>(),
                    jdbcTemplate.getDataSource().getConnection()
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=livros_por_autor.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(("Erro ao gerar relatório: " + e.getMessage()).getBytes());
        } finally {
            if (reportStream != null) {
                try {
                    reportStream.close();
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }
        }
    }
}