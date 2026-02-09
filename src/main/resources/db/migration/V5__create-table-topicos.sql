CREATE TABLE topicos (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     titulo VARCHAR(200) NOT NULL UNIQUE,
     contenido TEXT NOT NULL,
     fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     status TINYINT(1) NOT NULL DEFAULT 1,
     autor_id BIGINT NOT NULL,
     curso_id BIGINT NOT NULL,
     CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
     CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);