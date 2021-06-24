package clases;
import java.util.List;
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
        
        this.validarRankeo();
    }

    public IRankeable getRankeable() {
        //TODO
        return null;
    }
    
    // Privates

    private void validarRankeo() {
        //TODO
    }

	public Map<String, Integer> getCategorias() {
		return this.categorias;
	}


}
