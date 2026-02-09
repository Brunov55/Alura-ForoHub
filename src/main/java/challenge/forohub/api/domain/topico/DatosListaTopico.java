package challenge.forohub.api.domain.topico;

public record DatosListaTopico(
        String titulo,
        String autor,
        String curso,
        String fechaCreacion,
        String contenido
) {
    public DatosListaTopico(Topico topico) {
        this(
            topico.getTitulo(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre(),
            topico.getFechaCreacion().toString(),
            topico.getContenido()
        );
    }
}
