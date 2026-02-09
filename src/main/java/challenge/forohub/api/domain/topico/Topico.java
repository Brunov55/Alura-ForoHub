package challenge.forohub.api.domain.topico;

import challenge.forohub.api.domain.curso.Curso;
import challenge.forohub.api.domain.respuesta.Respuesta;
import challenge.forohub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String contenido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    private boolean status;
    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Respuesta> respuestas = new ArrayList<>();

    public void agregarRespuesta(Respuesta respuesta){
        respuestas.add(respuesta);
    }

    public void actualizarContenido(DatosActualizarTopico datos){
        if(datos.titulo() != null) this.titulo = datos.titulo();
        if(datos.contenido() != null) this.contenido = datos.contenido();
    }

    public void eliminarTopico(){
        this.status = false;
    }

    public Topico(String titulo, String contenido, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.autor = autor;
        this.curso = curso;
        this.status = true;
        this.fechaCreacion = LocalDateTime.now();
    }
}
