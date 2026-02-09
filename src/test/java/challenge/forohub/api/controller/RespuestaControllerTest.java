package challenge.forohub.api.controller;

import challenge.forohub.api.domain.respuesta.CreacionDeRespuestas;
import challenge.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import challenge.forohub.api.domain.respuesta.DatosDetalleRespuesta;
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
class RespuestaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosCreacionRespuesta> jsonCreacionRespuesta;

    @Autowired
    private JacksonTester<DatosDetalleRespuesta> jsonDetalleRespuesta;

    @MockitoBean
    private CreacionDeRespuestas creacionDeRespuestas;

    @Test
    @DisplayName("Debería devolver http 400 cuando la request no tenga datos")
    @WithMockUser
    void crearRespuestaEscenario1() throws Exception {
        var response = mvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando falte el topicoId")
    @WithMockUser
    void crearRespuestaEscenario2() throws Exception {
        var response = mvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "contenido": "Esta es mi respuesta al tópico"
                                }
                                """))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 cuando la request reciba un json válido")
    @WithMockUser
    void crearRespuestaEscenario3() throws Exception {
        var datosCreacion = new DatosCreacionRespuesta("Esta es una respuesta válida al tópico", 1L);
        var datosDetalle = new DatosDetalleRespuesta(1L, "Esta es una respuesta válida al tópico",
                "Usuario Test", LocalDateTime.now(), false, 1L, "Tópico de prueba");

        when(creacionDeRespuestas.crear(any())).thenReturn(datosDetalle);

        var response = mvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreacionRespuesta.write(datosCreacion).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Debería devolver http 403 cuando el usuario no esté autenticado")
    void crearRespuestaEscenario4() throws Exception {
        var response = mvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Debería devolver http 200 al listar respuestas de un tópico")
    @WithMockUser
    void listarRespuestasEscenario1() throws Exception {
        var response = mvc.perform(get("/respuestas/topico/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
