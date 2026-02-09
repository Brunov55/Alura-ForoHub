package challenge.forohub.api.domain.topico.validaciones.actualizacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosActualizarTopico;
import challenge.forohub.api.domain.topico.Topico;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAutorTopico implements IValidadorActualizacionTopico {

    @Override
    public void validar(Topico topico, DatosActualizarTopico datos) {
        Usuario usuarioActual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!topico.getAutor().getId().equals(usuarioActual.getId())) {
            throw new ValidacionException("Solo el autor puede editar este tópico");
        }
    }
}
