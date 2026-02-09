package challenge.forohub.api.infra.errores;

import challenge.forohub.api.domain.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GestorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DatosError> gestionarError404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DatosError("Recurso no encontrado", HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream()
                .map(DatosErrorValidacion::new)
                .toList());
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<DatosError> gestionarErrorDeValidacion(ValidacionException e) {
        return ResponseEntity.badRequest()
                .body(new DatosError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DatosError> gestionarErrorJsonInvalido(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest()
                .body(new DatosError("Error en el formato del JSON", HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DatosError> gestionarErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DatosError("Credenciales inválidas", HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DatosError> gestionarErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DatosError("Falla en la autenticación", HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DatosError> gestionarErrorAccesoDenegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new DatosError("Acceso denegado", HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DatosError> gestionarErrorArgumentoInvalido(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new DatosError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DatosError> gestionarError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DatosError("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    public record DatosErrorValidacion(String campo, String mensaje) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    public record DatosError(String mensaje, int status, LocalDateTime timestamp) {
        public DatosError(String mensaje, int status) {
            this(mensaje, status, LocalDateTime.now());
        }
    }
}
