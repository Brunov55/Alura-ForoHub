package challenge.forohub.api.domain.topico.validaciones.actualizacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosActualizarTopico;
import challenge.forohub.api.domain.topico.Topico;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoActivo implements IValidadorActualizacionTopico {

    @Override
    public void validar(Topico topico, DatosActualizarTopico datos) {
        if (!topico.isStatus()) {
            throw new ValidacionException("No se puede actualizar un tópico eliminado");
        }
    }
}
