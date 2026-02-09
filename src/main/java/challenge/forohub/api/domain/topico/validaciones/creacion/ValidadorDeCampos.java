package challenge.forohub.api.domain.topico.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import org.springframework.stereotype.Component;

@Component("ValidadorDeCamposDeTopicos")
public class ValidadorDeCampos implements IValidadorDeCreacionTopicos {
    public void validar(DatosCreacionTopico datos) {
        if(datos.titulo() == null || datos.titulo().isBlank() ||
        datos.contenido() == null || datos.contenido().isBlank())
            throw new ValidacionException("No se puede crear un topico con campos en blanco");
    }
}
