package br.jus.tj.livrosapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "autor")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}