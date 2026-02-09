package challenge.forohub.api.controller;

import challenge.forohub.api.domain.usuario.DatosRegistroUsuario;
import challenge.forohub.api.domain.usuario.RegistroUsuarioService;
import challenge.forohub.api.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroUsuarioService registroService;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriBuilder) {
        Usuario usuario = registroService.registrar(datos);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosRespuestaRegistro(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico()));
    }

    private record DatosRespuestaRegistro(Long id, String nombre, String correoElectronico) {}
}
