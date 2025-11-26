CREATE TABLE autor (
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE assunto (
    id        SERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE livro (
    id             SERIAL PRIMARY KEY,
    titulo         VARCHAR(150) NOT NULL,
    editora        VARCHAR(100),
    edicao         INTEGER,
    ano_publicacao VARCHAR(4),
    valor          NUMERIC(10,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT chk_ano_valido CHECK (ano_publicacao ~ '^[0-9]{4}$')
);

CREATE TABLE livro_autor (
    livro_id INTEGER NOT NULL REFERENCES livro(id)   ON DELETE CASCADE,
    autor_id INTEGER NOT NULL REFERENCES autor(id)   ON DELETE CASCADE,
    PRIMARY KEY (livro_id, autor_id)
);

CREATE TABLE livro_assunto (
    livro_id   INTEGER NOT NULL REFERENCES livro(id)     ON DELETE CASCADE,
    assunto_id INTEGER NOT NULL REFERENCES assunto(id)   ON DELETE CASCADE,
    PRIMARY KEY (livro_id, assunto_id)
);

CREATE OR REPLACE VIEW vw_relatorio_livros_por_autor AS
SELECT
    a.id           AS autor_id,
    a.nome         AS autor_nome,
    l.id           AS livro_id,
    l.titulo       AS livro_titulo,
    l.editora,
    l.edicao,
    l.ano_publicacao,
    l.valor
FROM autor a
JOIN livro_autor la ON a.id = la.autor_id
JOIN livro l ON la.livro_id = l.id
ORDER BY a.nome, l.titulo;

CREATE INDEX idx_livro_titulo ON livro(titulo);
CREATE INDEX idx_autor_nome   ON autor(nome);
CREATE INDEX idx_assunto_desc ON assunto(descricao);