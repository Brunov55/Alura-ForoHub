package challenge.forohub.api.domain.topico.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.curso.CursoRepository;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCursoExiste implements IValidadorDeCreacionTopicos {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DatosCreacionTopico datos) {
        if (datos.cursoId() == null) {
            throw new ValidacionException("El curso es obligatorio");
        }

        boolean cursoExiste = cursoRepository.existsById(datos.cursoId());
        if (!cursoExiste) {
            throw new ValidacionException("El curso con id " + datos.cursoId() + " no existe");
        }
    }
}
