package br.jus.tj.livrosapi.domain.repository;

import br.jus.tj.livrosapi.domain.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {}