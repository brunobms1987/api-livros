package br.jus.tj.livrosapi.domain.repository;

import br.jus.tj.livrosapi.domain.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {}