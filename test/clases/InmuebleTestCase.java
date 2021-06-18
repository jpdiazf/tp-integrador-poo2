package clases;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.CancelacionGratuita;
import clases.Foto;
import clases.Inmueble;
import clases.PrecioPeriodo;

class InmuebleTestCase {

	Inmueble casa;
	Inmueble habitacion;
	Inmueble departamento;
	
	@BeforeEach
	void setup() {
		casa = new Inmueble(null, "CASA", 40.0, "Argentina", "Quilmes","Mitre 406", Arrays.asList("WIFI","AIRE"),
						5, new ArrayList<Foto>(),LocalDateTime.of(0,1,1,10, 0), LocalDateTime.of(0,1,1,22,0), new CancelacionGratuita(),
						new ArrayList<String>(), 
						Arrays.asList(new PrecioPeriodo(LocalDate.of(2021, 5, 1), LocalDate.of(2021, 06, 1), 500.0)));
	}
	
	@Test
	void precioPorPeriodoTest() {
		
		double valor = casa.precioPorPeriodo(LocalDate.of(2021, 5, 5), LocalDate.of(2021, 5, 8));
		
		assertEquals(1500.0, valor);
		
	}

}
