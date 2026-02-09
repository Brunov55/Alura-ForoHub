package challenge.forohub.api.domain.respuesta.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import challenge.forohub.api.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoExiste implements IValidadorCreacionRespuesta {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCreacionRespuesta datos) {
        if (datos.topicoId() == null) {
            throw new ValidacionException("El tópico es obligatorio");
        }

        boolean existe = topicoRepository.existsById(datos.topicoId());
        if (!existe) {
            throw new ValidacionException("El tópico no existe");
        }
    }
}
