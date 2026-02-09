package challenge.forohub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String autor,
        String curso,
        String contenido,
        LocalDateTime fechaCreacion
) {
        public DatosDetalleTopico(Topico topico) {
            this(topico.getId(),
                    topico.getTitulo(),
                    topico.getAutor().getNombre(),
                    topico.getCurso().getNombre(),
                    topico.getContenido(),
                    topico.getFechaCreacion());
        }
}
