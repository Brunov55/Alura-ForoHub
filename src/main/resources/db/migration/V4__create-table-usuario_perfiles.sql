CREATE TABLE usuario_perfiles (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (perfil_id) REFERENCES perfiles(id)
);