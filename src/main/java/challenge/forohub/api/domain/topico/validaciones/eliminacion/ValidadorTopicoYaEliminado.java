package challenge.forohub.api.domain.topico.validaciones.eliminacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.Topico;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoYaEliminado implements IValidadorEliminacionTopico {

    @Override
    public void validar(Topico topico) {
        if (!topico.isStatus()) {
            throw new ValidacionException("El tópico ya fue eliminado");
        }
    }
}
