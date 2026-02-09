package challenge.forohub.api.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionRespuesta(
        @NotBlank
        String contenido,
        @NotNull
        Long topicoId
) {
}
