package challenge.forohub.api.domain.respuesta.validaciones.actualizacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.DatosActualizarRespuesta;
import challenge.forohub.api.domain.respuesta.Respuesta;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAutorRespuesta implements IValidadorActualizacionRespuesta {

    @Override
    public void validar(Respuesta respuesta, DatosActualizarRespuesta datos) {
        Usuario usuarioActual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!respuesta.getAutor().getId().equals(usuarioActual.getId())) {
            throw new ValidacionException("Solo el autor puede editar esta respuesta");
        }
    }
}
