package challenge.forohub.api.domain.topico.validaciones.actualizacion;

import challenge.forohub.api.domain.topico.DatosActualizarTopico;
import challenge.forohub.api.domain.topico.Topico;

public interface IValidadorActualizacionTopico {
    void validar(Topico topico, DatosActualizarTopico datos);
}
