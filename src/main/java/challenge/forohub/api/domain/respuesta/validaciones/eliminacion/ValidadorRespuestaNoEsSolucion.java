package challenge.forohub.api.domain.respuesta.validaciones.eliminacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.Respuesta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespuestaNoEsSolucion implements IValidadorEliminacionRespuesta {

    @Override
    public void validar(Respuesta respuesta) {
        if (respuesta.isSolucion()) {
            throw new ValidacionException("No se puede eliminar una respuesta marcada como solución");
        }
    }
}
