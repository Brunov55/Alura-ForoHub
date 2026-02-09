package challenge.forohub.api.domain.topico.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import org.springframework.stereotype.Component;

@Component
public class ValidadorLongitudContenido implements IValidadorDeCreacionTopicos {

    private static final int MIN_CARACTERES = 20;
    private static final int MAX_CARACTERES = 10000;

    @Override
    public void validar(DatosCreacionTopico datos) {
        if (datos.contenido() != null) {
            int longitud = datos.contenido().length();
            if (longitud < MIN_CARACTERES) {
                throw new ValidacionException("El contenido debe tener al menos " + MIN_CARACTERES + " caracteres");
            }
            if (longitud > MAX_CARACTERES) {
                throw new ValidacionException("El contenido no puede exceder " + MAX_CARACTERES + " caracteres");
            }
        }
    }
}
