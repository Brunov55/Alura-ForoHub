package challenge.forohub.api.controller;

import challenge.forohub.api.domain.curso.CursoRepository;
import challenge.forohub.api.domain.curso.DatosListaCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<List<DatosListaCurso>> listar() {
        var cursos = cursoRepository.findAll()
                .stream()
                .map(DatosListaCurso::new)
                .toList();
        return ResponseEntity.ok(cursos);
    }
}
