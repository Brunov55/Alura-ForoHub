package challenge.forohub.api.controller;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.respuesta.*;
import challenge.forohub.api.domain.respuesta.validaciones.actualizacion.IValidadorActualizacionRespuesta;
import challenge.forohub.api.domain.respuesta.validaciones.eliminacion.IValidadorEliminacionRespuesta;
import challenge.forohub.api.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private CreacionDeRespuestas creacionService;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private List<IValidadorActualizacionRespuesta> validadoresActualizacion;

    @Autowired
    private List<IValidadorEliminacionRespuesta> validadoresEliminacion;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> crear(@RequestBody @Valid DatosCreacionRespuesta datos) {
        var respuesta = creacionService.crear(datos);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<Page<DatosListaRespuesta>> listarPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        Page<DatosListaRespuesta> respuestas = respuestaRepository
                .findByTopicoId(topicoId, paginacion)
                .map(DatosListaRespuesta::new);
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> detallar(@PathVariable Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarRespuesta datos) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));

        validadoresActualizacion.forEach(v -> v.validar(respuesta, datos));

        respuesta.actualizarContenido(datos);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @PatchMapping("/{id}/solucion")
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> marcarComoSolucion(@PathVariable Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));

        Usuario usuarioActual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!respuesta.getTopico().getAutor().getId().equals(usuarioActual.getId())) {
            throw new ValidacionException("Solo el autor del tópico puede marcar una respuesta como solución");
        }

        respuesta.marcarComoSolucion();
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Respuesta no encontrada"));

        validadoresEliminacion.forEach(v -> v.validar(respuesta));

        respuestaRepository.delete(respuesta);
        return ResponseEntity.noContent().build();
    }
}
