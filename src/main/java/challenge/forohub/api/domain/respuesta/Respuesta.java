package challenge.forohub.api.domain.respuesta;

import challenge.forohub.api.domain.topico.Topico;
import challenge.forohub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private boolean solucion;

    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(String contenido, Usuario autor, Topico topico) {
        this.contenido = contenido;
        this.autor = autor;
        this.topico = topico;
        this.solucion = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void actualizarContenido(DatosActualizarRespuesta datos) {
        if (datos.contenido() != null) {
            this.contenido = datos.contenido();
        }
    }

    public void marcarComoSolucion() {
        this.solucion = true;
    }

    public void desmarcarComoSolucion() {
        this.solucion = false;
    }
}
