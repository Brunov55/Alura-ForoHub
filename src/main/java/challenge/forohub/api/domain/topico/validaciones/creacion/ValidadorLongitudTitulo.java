package challenge.forohub.api.domain.topico.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import org.springframework.stereotype.Component;

@Component
public class ValidadorLongitudTitulo implements IValidadorDeCreacionTopicos {

    private static final int MIN_CARACTERES = 10;
    private static final int MAX_CARACTERES = 200;

    @Override
    public void validar(DatosCreacionTopico datos) {
        if (datos.titulo() != null) {
            int longitud = datos.titulo().length();
            if (longitud < MIN_CARACTERES) {
                throw new ValidacionException("El título debe tener al menos " + MIN_CARACTERES + " caracteres");
            }
            if (longitud > MAX_CARACTERES) {
                throw new ValidacionException("El título no puede exceder " + MAX_CARACTERES + " caracteres");
            }
        }
    }
}
