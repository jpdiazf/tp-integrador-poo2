package clases;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InmuebleTestCase {
	Usuario dueño;
	Foto foto1;
	Foto foto2;
	PrecioPeriodo pp1;
	PrecioPeriodo pp2;
	Inmueble casa;
	Inmueble habitacion;
	Inmueble departamento;
	
	
	@BeforeEach
	void setup() {
		dueño = mock(Usuario.class);
		pp1 = new PrecioPeriodo(LocalDate.of(2021, 5, 1), LocalDate.of(2021, 6, 1), 500.0);
		pp2 = new PrecioPeriodo(LocalDate.of(2021, 8, 1), LocalDate.of(2021, 9, 1), 700.0);
		casa = new Inmueble(dueño, "CASA", 40.0, "Argentina", "Quilmes","Mitre 406", Arrays.asList("WIFI","AIRE"),
						5, Arrays.asList(foto1,foto2),LocalTime.of(11,00), LocalTime.of(22,0), new CancelacionGratuita(),
						Arrays.asList("Efectivo","Debito"), Arrays.asList(pp1,pp2));
	}

	@Test
	void constructorTest(){
		
		assertEquals(casa.getPropietario(), dueño);
		assertEquals(casa.getTipo(), "CASA");
		assertEquals(casa.getSuperficie(), 40.0);
		assertEquals(casa.getPais(), "Argentina");
		assertEquals(casa.getCiudad(), "Quilmes");
		assertEquals(casa.getDireccion(), "Mitre 406");
		assertEquals(casa.getServicios(), Arrays.asList("WIFI","AIRE"));
		assertEquals(casa.getCapacidad(), 5);
		assertEquals(casa.getFotos(), Arrays.asList(foto1,foto2));
		assertEquals(casa.getHorarioCheckIn(), LocalTime.of(11,00));
		assertEquals(casa.getHorarioCheckOut(), LocalTime.of(22,00));
		assertTrue(casa.getPoliticaCancelacion() instanceof CancelacionGratuita);
		assertEquals(casa.getFormasDePagoAceptadas(), Arrays.asList("Efectivo","Debito"));
		assertEquals(casa.getPrecios(), Arrays.asList(pp1, pp2));

	}
	
	@Test
	void precioPorPeriodoTest() {
		
		//Excercise
		double valor = casa.getPrecioPorPeriodo(LocalDate.of(2021, 5, 5), LocalDate.of(2021, 5, 8));
		
		//Verify
		assertEquals(1500.0, valor);
		
	}

	
	@Test
	void estaPublicadoTest() {
	
		//Excercise	
		boolean rangoInvalido = casa.estaPublicadoPeriodo(LocalDate.of(2021, 5, 15), LocalDate.of(2021, 06, 15));
		boolean rangoValido = casa.estaPublicadoPeriodo(LocalDate.of(2021, 5, 03), LocalDate.of(2021, 06, 1));
	
		//Verify
		assertFalse(rangoInvalido);
		assertTrue(rangoValido);

	}
	
	@Test
	void vecesAlquiladoTest() {
	
		//Excercise	
		Integer ceroVecesAlquilado = casa.getVecesAlquilado();
		casa.sumarVezAlquilado();
		casa.sumarVezAlquilado();
		Integer dosVecesAlquilado = casa.getVecesAlquilado();

	
		//Verify
		assertEquals(0,ceroVecesAlquilado);
		assertEquals(2,dosVecesAlquilado);

	}
	
	@Test
	void rankeoTest() {
	
		//Setup
		Map<String, Integer> categorias1 = new HashMap<String, Integer>();
		Map<String, Integer> categorias2 = new HashMap<String, Integer>();
		Map<String, Integer> categorias3 = new HashMap<String, Integer>();
		Rankeo r1 = new Rankeo(null, "Bueno", categorias1);
		Rankeo r2 = new Rankeo(null, "Regular", categorias2);
		Rankeo r3 = new Rankeo(null, "Malo", categorias3);
		
		categorias1.put("Comodidad", 5);
		categorias1.put("Limpieza", 4);
		categorias1.put("Espacio", 3);
		categorias2.put("Comodidad", 4);
		categorias2.put("Limpieza", 2);
		categorias2.put("Espacio", 5);
		categorias3.put("Comodidad", 1);
		categorias3.put("Espacio", 3);
		
		casa.addRankeo(r1);
		casa.addRankeo(r2);
		casa.addRankeo(r3);
	
		//Excercise	
		List<Rankeo> rankeos = casa.getRankeos();
		double puntajeComodidad = casa.promedioRanking("Comodidad");
		double puntajeTotal = casa.promedioTotalRanking();
			
		//Verify
		assertEquals(Arrays.asList(r1,r2,r3),rankeos);
		assertEquals(3.33,puntajeComodidad);
		assertEquals(3.22,puntajeTotal);
	}
	
}
