# ForoHub API

## Descripcion

ForoHub es una API REST desarrollada como parte del Challenge Backend del programa Oracle ONE en colaboracion con Alura Latam. El proyecto simula un foro de discusion donde los usuarios pueden crear topicos, responder a ellos y marcar respuestas como solucion.

Este proyecto fue desarrollado por un estudiante del programa Oracle ONE como parte del desafio "Challenge ForoHub".

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 4.0.2
- Spring Security con JWT
- Spring Data JPA
- MySQL
- Flyway (migraciones de base de datos)
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- JUnit 5 y Mockito (testing)

---

## Estructura del Proyecto

```
src/main/java/challenge/forohub/api/
├── ForoHubApplication.java
├── controller/
│   ├── AutenticationController.java
│   ├── CursoController.java
│   ├── RegistroController.java
│   ├── RespuestaController.java
│   └── TopicoController.java
├── domain/
│   ├── ValidacionException.java
│   ├── curso/
│   ├── perfil/
│   ├── respuesta/
│   ├── topico/
│   └── usuario/
└── infra/
    ├── errores/
    ├── security/
    └── springdoc/
```

---

## Endpoints de la API

### Autenticacion

| Metodo | Endpoint    | Descripcion                  | Autenticacion |
|--------|-------------|------------------------------|---------------|
| POST   | /login      | Iniciar sesion               | No            |
| POST   | /registro   | Registrar nuevo usuario      | No            |

### Topicos

| Metodo | Endpoint      | Descripcion                  | Autenticacion |
|--------|---------------|------------------------------|---------------|
| POST   | /topicos      | Crear nuevo topico           | Si            |
| GET    | /topicos      | Listar todos los topicos     | Si            |
| GET    | /topicos/{id} | Obtener detalle de un topico | Si            |
| PUT    | /topicos/{id} | Actualizar un topico         | Si            |
| DELETE | /topicos/{id} | Eliminar un topico           | Si            |

### Respuestas

| Metodo | Endpoint                   | Descripcion                      | Autenticacion |
|--------|----------------------------|----------------------------------|---------------|
| POST   | /respuestas                | Crear nueva respuesta            | Si            |
| GET    | /respuestas/topico/{id}    | Listar respuestas de un topico   | Si            |
| GET    | /respuestas/{id}           | Obtener detalle de una respuesta | Si            |
| PUT    | /respuestas/{id}           | Actualizar una respuesta         | Si            |
| PATCH  | /respuestas/{id}/solucion  | Marcar como solucion             | Si            |
| DELETE | /respuestas/{id}           | Eliminar una respuesta           | Si            |

### Cursos

| Metodo | Endpoint | Descripcion               | Autenticacion |
|--------|----------|---------------------------|---------------|
| GET    | /cursos  | Listar cursos disponibles | Si            |

---

## Modelo de Base de Datos

El proyecto utiliza las siguientes tablas:

- **usuarios**: Almacena la informacion de los usuarios registrados
- **perfiles**: Define los roles de usuario (ADMIN, USER)
- **usuario_perfiles**: Tabla intermedia para la relacion muchos a muchos
- **cursos**: Catalogo de cursos de programacion disponibles
- **topicos**: Temas de discusion creados por los usuarios
- **respuestas**: Respuestas a los topicos

### Diagrama de Relaciones

```
Usuario (1) ---- (N) Topico
Usuario (1) ---- (N) Respuesta
Topico  (1) ---- (N) Respuesta
Curso   (1) ---- (N) Topico
Usuario (N) ---- (N) Perfil
```

---

## Configuracion del Proyecto

### Requisitos Previos

- JDK 17 o superior
- MySQL 8.0 o superior
- Maven 3.8 o superior

### Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE forohub;
CREATE DATABASE forohub_test;
```

### Variables de Entorno

Configurar en `application.properties` para desarrollo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
api.security.token.secret=tu_clave_secreta_jwt
```

### Variables de Entorno para Produccion

El archivo `application-prod.properties` utiliza variables de entorno que deben ser configuradas segun las necesidades del usuario:

| Variable | Descripcion | Ejemplo |
|----------|-------------|---------|
| DB_URL | URL de conexion a la base de datos | jdbc:mysql://localhost:3306/forohub |
| DB_USER | Usuario de la base de datos | root |
| DB_PASSWORD | Contrasena de la base de datos | mi_password |
| JWT_SECRET | Clave secreta para firmar tokens JWT | clave_secreta_segura |

Para configurar las variables en Linux/Mac:

```bash
export DB_URL="jdbc:mysql://localhost:3306/forohub"
export DB_USER="tu_usuario"
export DB_PASSWORD="tu_password"
export JWT_SECRET="tu_clave_secreta"
export SPRING_PROFILES_ACTIVE="prod"
```

Para ejecutar con el perfil de produccion:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Nota: Los nombres de las variables pueden ser modificados en el archivo `application-prod.properties` segun las preferencias del usuario.

### Ejecutar el Proyecto

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/ForoHub.git

# Navegar al directorio
cd ForoHub

# Compilar el proyecto
./mvnw clean install

# Ejecutar la aplicacion
./mvnw spring-boot:run
```

---

## Documentacion de la API

Una vez ejecutado el proyecto, la documentacion interactiva esta disponible en:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

## Validaciones Implementadas

### Topicos

- Titulo obligatorio (minimo 10 caracteres, maximo 200)
- Contenido obligatorio (minimo 20 caracteres, maximo 5000)
- Curso debe existir
- No se permiten topicos duplicados (mismo titulo y contenido)

### Respuestas

- Contenido obligatorio (minimo 10 caracteres, maximo 5000)
- El topico debe existir y estar activo
- Solo el autor puede editar su respuesta
- Solo el autor del topico puede marcar una respuesta como solucion
- No se puede eliminar una respuesta marcada como solucion

### Usuarios

- Nombre obligatorio
- Correo electronico obligatorio y con formato valido
- Contrasena obligatoria (minimo 6 caracteres)

---

## Seguridad

El proyecto implementa autenticacion basada en JWT (JSON Web Tokens):

1. El usuario se registra en `/registro`
2. El usuario inicia sesion en `/login` y recibe un token JWT
3. El token debe incluirse en el header `Authorization: Bearer {token}`
4. El token tiene una expiracion de 2 horas

---

## Tests

El proyecto incluye tests unitarios para los controllers:

```bash
# Ejecutar todos los tests
./mvnw test
```

Tests disponibles:

- AutenticacionControllerTest
- TopicoControllerTest
- RespuestaControllerTest
- RegistroControllerTest
- CursoControllerTest

---

## Migraciones de Base de Datos

Las migraciones se ejecutan automaticamente con Flyway:

| Version | Descripcion                  |
|---------|------------------------------|
| V1      | Crear tabla perfiles         |
| V2      | Crear tabla cursos           |
| V3      | Crear tabla usuarios         |
| V4      | Crear tabla usuario_perfiles |
| V5      | Crear tabla topicos          |
| V6      | Crear tabla respuestas       |

---

## Manejo de Errores

La API devuelve respuestas estructuradas para todos los errores:

```json
{
    "mensaje": "Descripcion del error",
    "status": 400,
    "timestamp": "2026-02-08T23:00:00"
}
```

Codigos de estado utilizados:

| Codigo | Descripcion           |
|--------|-----------------------|
| 200    | Operacion exitosa     |
| 201    | Recurso creado        |
| 400    | Error de validacion   |
| 401    | No autenticado        |
| 403    | Acceso denegado       |
| 404    | Recurso no encontrado |
| 500    | Error del servidor    |

---

## Autor

Proyecto realizado por Bruno Victoria como parte del Challenge ForoHub del programa Oracle ONE en colaboracion con Alura Latam.

---

## Proposito

Este proyecto fue creado con fines educativos como parte del programa Oracle ONE.
