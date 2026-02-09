package challenge.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(
        Long id,
        String contenido,
        String autor,
        LocalDateTime fechaCreacion,
        boolean solucion
) {
    public DatosListaRespuesta(Respuesta respuesta) {
        this(
            respuesta.getId(),
            respuesta.getContenido(),
            respuesta.getAutor().getNombre(),
            respuesta.getFechaCreacion(),
            respuesta.isSolucion()
        );
    }
}
