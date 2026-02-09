package challenge.forohub.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato del correo electrónico es inválido")
        String correoElectronico,
        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena
) {
}
