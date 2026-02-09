CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contenido TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    solucion TINYINT(1) NOT NULL DEFAULT 0,
    autor_id BIGINT NOT NULL,
    topico_id BIGINT NOT NULL,
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_respuesta_topico FOREIGN KEY (topico_id) REFERENCES topicos(id)
);