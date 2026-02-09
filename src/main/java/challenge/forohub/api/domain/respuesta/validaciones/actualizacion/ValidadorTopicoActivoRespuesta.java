package challenge.forohub.api.domain.respuesta.validaciones.actualizacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.DatosActualizarRespuesta;
import challenge.forohub.api.domain.respuesta.Respuesta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoActivoRespuesta implements IValidadorActualizacionRespuesta {

    @Override
    public void validar(Respuesta respuesta, DatosActualizarRespuesta datos) {
        if (!respuesta.getTopico().isStatus()) {
            throw new ValidacionException("No se puede editar una respuesta de un tópico eliminado");
        }
    }
}
