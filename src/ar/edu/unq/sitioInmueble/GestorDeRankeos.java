package ar.edu.unq.sitioInmueble;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import clases.Rankeo;
import interfaces.IRankeable;

public class GestorDeRankeos {

    private IRankeable rankeable;
    private List<Rankeo> rankeos;

    public GestorDeRankeos() {
    	this.rankeos = new ArrayList<Rankeo>();
    }
    
    public List<Rankeo> getRankeos() {
    	return this.rankeos;
    }
    
    public void addRankeo(Rankeo rankeo) {
    	this.validarRankeo(rankeo);
    	this.rankeos.add(rankeo);
    }
    
    public double promedioRanking(String categoria) {

        double sum = 0;
        double count = 0;

        for(Rankeo rankeo:rankeos) {
        	Map<String, Integer> mapCategorias = rankeo.getCategorias();
        	if(mapCategorias.containsKey(categoria)) {
        		sum += mapCategorias.get(categoria);
        		count ++;
        	}
        }

        if(count == 0) {
        	return 0;
        } else {
        	return  Math.floor((sum / count) * 100) / 100;
        }
    }
    
    public String textoComentarios() {
    	String textoComentarios = "";
    	
    	for(Rankeo rankeo : rankeos) {
    		textoComentarios += rankeo.getComentario();
    	}
    	
    	return textoComentarios;
    }


    public double promedioTotalRanking() {

        double sum = 0d;

        for(Rankeo rankeo:rankeos) {
            sum += this.promedioPuntaje(rankeo);
        }

        if(rankeos.isEmpty()) {
        	return 0;
        } else {
        	return Math.floor((sum / rankeos.size()) * 100) / 100;
        }
     }
    
    public void validarRankeo(Rankeo rankeo) {
		// TODO Auto-generated method stub
		
	}
    
    private double promedioPuntaje(Rankeo rankeo) {
    	
    	Set<String> categorias = rankeo.getCategorias().keySet();
    	double sum = 0d;

    	for(String c : categorias) {
    		sum += rankeo.getCategorias().get(c);
    	}
    	
        if(categorias.isEmpty()) {
        	return 0;
        } else {
        	return Math.floor( (sum / categorias.size() ) * 100) / 100;
        }

    }

	
}
