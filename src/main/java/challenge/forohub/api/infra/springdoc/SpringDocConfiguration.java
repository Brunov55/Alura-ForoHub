package challenge.forohub.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info()
                        .title("ForoHub API")
                        .description("""
                                API REST para un foro de programación donde los usuarios pueden:
                                
                                Autenticar: Registro de usuarios y login con JWT
                                Tópicos: Crear, listar, actualizar y eliminar tópicos de discusión
                                Respuestas: Responder a tópicos y marcar respuestas como solución
                                Cursos: Consultar cursos disponibles (Java, Python, JavaScript, etc.)
                                
                                Tecnologías: Spring Boot 4, Spring Security, JWT, MySQL, Flyway
                                """)
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@forohub.com")));
    }
}
