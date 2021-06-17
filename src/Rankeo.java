import java.util.Map;

public class Rankeo {
    private Usuario usuario;
    private String comentario;
    private Map<String, Integer> categorias;

    public Rankeo(Usuario usuario, String comentario, Map<String, Integer> categorias) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.categorias = categorias;
        
        // TODO: lanzar excepci�n.
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
}
