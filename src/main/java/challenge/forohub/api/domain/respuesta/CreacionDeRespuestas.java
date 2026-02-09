package challenge.forohub.api.domain.respuesta;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.validaciones.creacion.IValidadorCreacionRespuesta;
import challenge.forohub.api.domain.topico.Topico;
import challenge.forohub.api.domain.topico.TopicoRepository;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreacionDeRespuestas {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<IValidadorCreacionRespuesta> validadores;

    public DatosDetalleRespuesta crear(DatosCreacionRespuesta datos) {
        validadores.forEach(v -> v.validar(datos));

        Topico topico = topicoRepository.findById(datos.topicoId())
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));


        Usuario autor = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Respuesta respuesta = new Respuesta(datos.contenido(), autor, topico);
        respuestaRepository.save(respuesta);

        return new DatosDetalleRespuesta(respuesta);
    }
}
