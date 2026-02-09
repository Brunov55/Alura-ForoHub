package challenge.forohub.api.domain.topico;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.curso.Curso;
import challenge.forohub.api.domain.curso.CursoRepository;
import challenge.forohub.api.domain.topico.validaciones.creacion.IValidadorDeCreacionTopicos;
import challenge.forohub.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreacionDeTopicos {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<IValidadorDeCreacionTopicos> validadores;

    public DatosDetalleTopico crear(DatosCreacionTopico datos){
        validadores.forEach(v -> v.validar(datos));

        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new ValidacionException("Curso no encontrado"));

        Usuario autor = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var topico = new Topico(datos.titulo(), datos.contenido(), autor, curso);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }
}
