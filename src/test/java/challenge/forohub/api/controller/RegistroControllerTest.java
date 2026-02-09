package challenge.forohub.api.controller;

import challenge.forohub.api.domain.usuario.DatosRegistroUsuario;
import challenge.forohub.api.domain.usuario.RegistroUsuarioService;
import challenge.forohub.api.domain.usuario.Usuario;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class RegistroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosRegistroUsuario> jsonRegistro;

    @MockitoBean
    private RegistroUsuarioService registroService;

    @Test
    @DisplayName("Debería devolver http 400 cuando la request no tenga datos")
    void registroEscenario1() throws Exception {
        var response = mvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando el email sea inválido")
    void registroEscenario2() throws Exception {
        var response = mvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Test User",
                                    "correoElectronico": "email-invalido",
                                    "contrasena": "123456"
                                }
                                """))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 400 cuando falte la contraseña")
    void registroEscenario3() throws Exception {
        var response = mvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre": "Test User",
                                    "correoElectronico": "test@forohub.com"
                                }
                                """))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería devolver http 201 cuando el registro sea exitoso")
    void registroEscenario4() throws Exception {
        var datosRegistro = new DatosRegistroUsuario("Test User", "test@forohub.com", "123456");

        when(registroService.registrar(any())).thenReturn(crearUsuarioMock());

        var response = mvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRegistro.write(datosRegistro).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    private Usuario crearUsuarioMock() {
        return new Usuario("Test User", "test@forohub.com", "hashedPassword");
    }
}
