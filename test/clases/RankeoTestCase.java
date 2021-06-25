package clases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.IRankeable;

class RankeoTestCase {
	Rankeo rankeo; 
	IRankeable habitacion;
	
	@BeforeEach
	void setUp() {		
		habitacion = mock(IRankeable.class);		
	}
	
	@Test
	void testConstructor() {
		Map<String, Integer> puntajes = new HashMap<String, Integer>();
		puntajes.put("Amabilidad", 10);
		rankeo = new Rankeo(habitacion, "Muy bueno", puntajes);
		
		assertEquals("Muy bueno", rankeo.getComentario());
		assertEquals(habitacion, rankeo.getRankeable());
		assertEquals(10, rankeo.getPuntajeCategoria("Amabilidad"));
	}
}
