package challenge.forohub.api.domain.respuesta.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ValidadorLongitudRespuesta implements IValidadorCreacionRespuesta {

    private static final int MIN_CARACTERES = 10;
    private static final int MAX_CARACTERES = 5000;

    @Override
    public void validar(DatosCreacionRespuesta datos) {
        if (datos.contenido() != null) {
            int longitud = datos.contenido().length();
            if (longitud < MIN_CARACTERES) {
                throw new ValidacionException("La respuesta debe tener al menos " + MIN_CARACTERES + " caracteres");
            }
            if (longitud > MAX_CARACTERES) {
                throw new ValidacionException("La respuesta no puede exceder " + MAX_CARACTERES + " caracteres");
            }
        }
    }
}
