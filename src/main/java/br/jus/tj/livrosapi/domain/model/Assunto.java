package br.jus.tj.livrosapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assunto")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}