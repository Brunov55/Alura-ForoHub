package challenge.forohub.api.controller;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.topico.*;
import challenge.forohub.api.domain.topico.validaciones.actualizacion.IValidadorActualizacionTopico;
import challenge.forohub.api.domain.topico.validaciones.eliminacion.IValidadorEliminacionTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private CreacionDeTopicos topico;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<IValidadorActualizacionTopico> validadoresActualizacion;

    @Autowired
    private List<IValidadorEliminacionTopico> validadoresEliminacion;

    @PostMapping
    @Transactional
    public ResponseEntity crearTopico(@RequestBody @Valid DatosCreacionTopico datos){
        var detalleTopico = topico.crear(datos);
        return ResponseEntity.ok(detalleTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(@PageableDefault(size = 5, sort = {"titulo"}) Pageable paginacion){
        Page<DatosListaTopico> pagina = topicoRepository.findAllByStatusTrue(paginacion).map(DatosListaTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarContenido(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));

        validadoresActualizacion.forEach(v -> v.validar(topico, datos));

        topico.actualizarContenido(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity borrar(@PathVariable Long id){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));

        validadoresEliminacion.forEach(v -> v.validar(topico));

        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("Tópico no encontrado"));

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
}
