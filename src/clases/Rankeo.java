package clases;
import java.util.Map;

import interfaces.IRankeable;

public class Rankeo {
    private IRankeable rankeable;
    private String comentario;
    private Map<String, Integer> categorias;

    public Rankeo(IRankeable rankeable, String comentario, Map<String, Integer> categorias) {
    	this.rankeable = rankeable;
        this.comentario = comentario;
        this.categorias = categorias;
    }
    
    public String getComentario() {
    	return this.comentario;
    }
    
    public IRankeable getRankeable() {
    	return rankeable;
    }
    
    public Map<String, Integer> getCategorias() {
    	return this.categorias;
    }
    
    public String textoRankeo() {
    	return null; // TODO
    }
}
