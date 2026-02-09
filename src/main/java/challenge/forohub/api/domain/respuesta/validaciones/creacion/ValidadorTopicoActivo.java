package challenge.forohub.api.domain.respuesta.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.DatosCreacionRespuesta;
import challenge.forohub.api.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ValidadorTopicoActivoRespuesta")
public class ValidadorTopicoActivo implements IValidadorCreacionRespuesta {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCreacionRespuesta datos) {
        var topico = topicoRepository.findById(datos.topicoId());

        if (topico.isPresent() && !topico.get().isStatus()) {
            throw new ValidacionException("No se puede responder a un tópico eliminado");
        }
    }
}
