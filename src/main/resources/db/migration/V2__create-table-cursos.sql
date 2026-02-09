CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

-- Cursos iniciales
INSERT INTO cursos (nombre, categoria) VALUES ('Java Básico', 'JAVA');
INSERT INTO cursos (nombre, categoria) VALUES ('Java Avanzado', 'JAVA');
INSERT INTO cursos (nombre, categoria) VALUES ('Python para principiantes', 'PYTHON');
INSERT INTO cursos (nombre, categoria) VALUES ('JavaScript moderno', 'JAVASCRIPT');
INSERT INTO cursos (nombre, categoria) VALUES ('TypeScript desde cero', 'TYPESCRIPT');
INSERT INTO cursos (nombre, categoria) VALUES ('Programación en C', 'C');
INSERT INTO cursos (nombre, categoria) VALUES ('C++ Orientado a Objetos', 'CPP');
INSERT INTO cursos (nombre, categoria) VALUES ('C# y .NET', 'CSHARP');
INSERT INTO cursos (nombre, categoria) VALUES ('Go para backend', 'GO');
INSERT INTO cursos (nombre, categoria) VALUES ('Rust systems programming', 'RUST');
INSERT INTO cursos (nombre, categoria) VALUES ('Kotlin para Android', 'KOTLIN');
INSERT INTO cursos (nombre, categoria) VALUES ('Swift para iOS', 'SWIFT');
