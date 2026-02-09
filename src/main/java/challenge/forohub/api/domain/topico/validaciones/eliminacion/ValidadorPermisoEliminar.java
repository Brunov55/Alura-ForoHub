package challenge.forohub.api.domain.topico.validaciones.eliminacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.Topico;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPermisoEliminar implements IValidadorEliminacionTopico {

    @Override
    public void validar(Topico topico) {
        Usuario usuarioActual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean esAutor = topico.getAutor().getId().equals(usuarioActual.getId());
        boolean esAdmin = usuarioActual.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!esAutor && !esAdmin) {
            throw new ValidacionException("Solo el autor o un administrador puede eliminar este tópico");
        }
    }
}
