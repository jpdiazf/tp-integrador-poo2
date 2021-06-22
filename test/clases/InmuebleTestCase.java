package clases;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InmuebleTestCase {

	Inmueble casa;
	Inmueble habitacion;
	Inmueble departamento;
	
	@BeforeEach
	void setup() {
		casa = new Inmueble(null, "CASA", 40.0, "Argentina", "Quilmes","Mitre 406", Arrays.asList("WIFI","AIRE"),
						5, new ArrayList<Foto>(),LocalTime.of(11,00), LocalTime.of(22,0), new CancelacionGratuita(),
						new ArrayList<String>(), Arrays.asList(new PrecioPeriodo(LocalDate.of(2021, 5, 1), 
						LocalDate.of(2021, 06, 1), 500.0)));
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
	
}
