package challenge.forohub.api.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String contenido,
        String autor,
        LocalDateTime fechaCreacion,
        boolean solucion,
        Long topicoId,
        String topicoTitulo
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(
            respuesta.getId(),
            respuesta.getContenido(),
            respuesta.getAutor().getNombre(),
            respuesta.getFechaCreacion(),
            respuesta.isSolucion(),
            respuesta.getTopico().getId(),
            respuesta.getTopico().getTitulo()
        );
    }
}
