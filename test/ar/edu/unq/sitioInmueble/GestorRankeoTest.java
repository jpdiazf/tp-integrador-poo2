package ar.edu.unq.sitioInmueble;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Rankeo;

class GestorRankeoTest {

	GestorDeRankeos gr; 
	GestorDeRankeos grVacio; 
	Rankeo r1;
	Rankeo r2;
	Rankeo r3;
	Rankeo r4;
	Map<String, Integer> categorias1;
	Map<String, Integer> categorias2;
	Map<String, Integer> categorias3;
	
	@BeforeEach
	void setup() {
		categorias1 = new HashMap<String, Integer>();
		categorias2 = new HashMap<String, Integer>();
		categorias3 = new HashMap<String, Integer>();
		gr = new GestorDeRankeos();
		grVacio = new GestorDeRankeos();
				
		categorias1.put("TiempoDeRespuesta", 5);
		categorias1.put("Amabilidad", 4);
		categorias1.put("Seriedad", 3);
		categorias2.put("TiempoDeRespuesta", 4);
		categorias2.put("Amabilidad", 2);
		categorias2.put("Seriedad", 5);
		categorias3.put("TiempoDeRespuesta", 1);
		categorias3.put("Seriedad", 3);
		
		r1 = new Rankeo(null, "Bueno", categorias1);
		r2 = new Rankeo(null, "Regular", categorias2);
		r3 = new Rankeo(null, "Malo", categorias3);
		r4 = new Rankeo(null, "Malo", new HashMap<String, Integer>());
		
		gr.addRankeo(r1);
		gr.addRankeo(r2);
		gr.addRankeo(r3);
	}
	
	@Test
	void textoComentariosTest() {
		assertEquals("Bueno. Regular. Malo. ", gr.textoComentarios());
	}
	
	@Test
	void promedioRankeingTest() {
		//Excersice
		double tRespuesta = gr.promedioRanking("TiempoDeRespuesta");
		double amabilidad = gr.promedioRanking("Amabilidad");
		double seriedad = gr.promedioRanking("Seriedad");
		double categoriaNoExistente = gr.promedioRanking("categoriaNoExistente");
		
		//Verify
		assertEquals(3.33,tRespuesta);
		assertEquals(3,amabilidad);
		assertEquals(3.66,seriedad);
		assertEquals(0,categoriaNoExistente);
	}
	
	@Test
	void promedioTotaRankingTest() {
		//Excersice
		double promedioTotal = gr.promedioTotalRanking();
		double promedioTotalVacio = grVacio.promedioTotalRanking();
		
		//Verify
		assertEquals(3.22,promedioTotal);
		assertEquals(0,promedioTotalVacio);
	}

	@Test
	void getRankeosTest() {
		//Excersice
		List<Rankeo> rankeos = gr.getRankeos();
		
		//Verify
		assertEquals(Arrays.asList(r1,r2,r3),rankeos);
	}	

}
