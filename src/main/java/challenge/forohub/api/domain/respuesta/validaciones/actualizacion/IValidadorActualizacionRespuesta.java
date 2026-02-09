package challenge.forohub.api.domain.respuesta.validaciones.actualizacion;

import challenge.forohub.api.domain.respuesta.DatosActualizarRespuesta;
import challenge.forohub.api.domain.respuesta.Respuesta;

public interface IValidadorActualizacionRespuesta {
    void validar(Respuesta respuesta, DatosActualizarRespuesta datos);
}
