package challenge.forohub.api.controller;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.CreacionDeTopicos;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import challenge.forohub.api.domain.topico.DatosDetalleTopico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosCreacionTopico> jsonCreacionTopico;

    @Autowired
    private JacksonTester<DatosDetalleTopico> jsonDetalleTopico;

    @MockitoBean
    private CreacionDeTopicos creacionDeTopicos;

    @Test
    @DisplayName("Debería devolver http 400 cuando la request no tenga datos")
    @WithMockUser
    void crearTopicoEscenario1() throws Exception {
        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando falte el cursoId")
    @WithMockUser
    void crearTopicoEscenario2() throws Exception {
        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "titulo": "Duda con Java",
                                    "contenido": "¿Cómo usar streams en Java?"
                                }
                                """))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 cuando la request reciba un json válido")
    @WithMockUser
    void crearTopicoEscenario3() throws Exception {
        var datosCreacion = new DatosCreacionTopico("Duda con Java Streams", "¿Cómo usar filter y map en Java?", 1L);
        var datosDetalle = new DatosDetalleTopico(1L, "Duda con Java Streams", "Usuario Test", "Java Básico",
                "¿Cómo usar filter y map en Java?", LocalDateTime.now());

        when(creacionDeTopicos.crear(any())).thenReturn(datosDetalle);

        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreacionTopico.write(datosCreacion).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Debería devolver http 403 cuando el usuario no esté autenticado")
    void crearTopicoEscenario4() throws Exception {
        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 al listar tópicos")
    @WithMockUser
    void listarTopicosEscenario1() throws Exception {
        var response = mvc.perform(get("/topicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando el título sea muy corto")
    @WithMockUser
    void crearTopicoEscenario5() throws Exception {
        when(creacionDeTopicos.crear(any()))
                .thenThrow(new ValidacionException("El título debe tener al menos 10 caracteres"));

        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "titulo": "Hola",
                                    "contenido": "¿Cómo usar streams en Java correctamente?",
                                    "cursoId": 1
                                }
                                """))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
