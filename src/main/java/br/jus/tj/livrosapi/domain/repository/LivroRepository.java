package br.jus.tj.livrosapi.domain.repository;

import br.jus.tj.livrosapi.domain.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {}