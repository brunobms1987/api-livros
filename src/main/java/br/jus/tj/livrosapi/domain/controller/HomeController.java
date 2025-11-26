package br.jus.tj.livrosapi.domain.controller;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sf.jasperreports.engine.*;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/api/relatorio")
    public ResponseEntity<ByteArrayResource> relatorio() throws JRException, SQLException {
        InputStream reportStream = getClass().getResourceAsStream("/reports/relatorio_livros_por_autor.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, new HashMap<>(), jdbcTemplate.getDataSource().getConnection());

        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

        ByteArrayResource resource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=livros_por_autor.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}