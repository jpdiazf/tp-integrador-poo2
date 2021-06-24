package clases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ar.edu.unq.sitioInmueble.GestorDeRankeos;

class GestorRankeoTest {

	GestorDeRankeos gr; 
	Rankeo r1;
	Rankeo r2;
	Rankeo r3;
	Map<String, Integer> categorias1;
	Map<String, Integer> categorias2;
	Map<String, Integer> categorias3;
	
	@Test
	void test() {
		//Setup
		categorias1 = new HashMap<String, Integer>();
		categorias2 = new HashMap<String, Integer>();
		categorias3 = new HashMap<String, Integer>();
		gr = new GestorDeRankeos();
				
		categorias1.put("TiempoDeRespuesta", 5);
		categorias1.put("Amabilidad", 4);
		categorias1.put("Seriedad", 3);
		categorias2.put("TiempoDeRespuesta", 4);
		categorias2.put("Amabilidad", 2);
		categorias2.put("Seriedad", 5);
		categorias3.put("TiempoDeRespuesta", 1);
		categorias3.put("Seriedad", 3);
		
		r1 = new Rankeo(null, null, categorias1);
		r2 = new Rankeo(null, null, categorias2);
		r3 = new Rankeo(null, null, categorias3);
		
		gr.addRankeo(r1);
		gr.addRankeo(r2);
		gr.addRankeo(r3);

		//Excersice
		double tRespuesta = gr.promedioRanking("TiempoDeRespuesta");
		double amabilidad = gr.promedioRanking("Amabilidad");
		double seriedad = gr.promedioRanking("Seriedad");
		double promedioTotal = gr.promedioTotalRanking();
		
		//Verify
		assertEquals(3.33,tRespuesta);
		assertEquals(3,amabilidad);
		assertEquals(3.66,seriedad);
		assertEquals(3.22,promedioTotal);
	}

}
