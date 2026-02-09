package challenge.forohub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCreacionTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String contenido,
        @NotNull
        Long cursoId
) {
}
