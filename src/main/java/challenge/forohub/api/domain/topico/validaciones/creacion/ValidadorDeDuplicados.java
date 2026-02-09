package challenge.forohub.api.domain.topico.validaciones.creacion;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.DatosCreacionTopico;
import challenge.forohub.api.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ValidadorDeDuplicadosDeTopicos")
public class ValidadorDeDuplicados implements IValidadorDeCreacionTopicos {
    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(DatosCreacionTopico datos) {
        boolean existeDuplicado = topicoRepository.existsByTituloOrContenidoContainsIgnoreCase(
                datos.titulo(),
                datos.contenido()
        );

        if(existeDuplicado) throw new ValidacionException("Ya existe un topico con ese titulo y/o contenido");
    }
}
