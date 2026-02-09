package challenge.forohub.api.domain.respuesta.validaciones.eliminacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.Respuesta;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPermisoEliminarRespuesta implements IValidadorEliminacionRespuesta {

    @Override
    public void validar(Respuesta respuesta) {
        Usuario usuarioActual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean esAutor = respuesta.getAutor().getId().equals(usuarioActual.getId());
        boolean esAdmin = usuarioActual.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!esAutor && !esAdmin) {
            throw new ValidacionException("Solo el autor o un administrador puede eliminar esta respuesta");
        }
    }
}
