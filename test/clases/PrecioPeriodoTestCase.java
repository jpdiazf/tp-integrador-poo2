package clases;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.PrecioPeriodo;

class PrecioPeriodoTestCase {

	PrecioPeriodo pp;
	
	@BeforeEach
	void setup() {
		pp = new PrecioPeriodo(LocalDate.of(2021, 1, 1), LocalDate.of(2021,1,31), 500);
	}
	
	@Test
	void constructorTest() {

		//Excersice - Verify 
		assertEquals(pp.comienzo(),LocalDate.of(2021, 1, 1));
		assertEquals(pp.fin(),LocalDate.of(2021,1,31));
		assertEquals(pp.valor,500.0);
	}
	
	@Test
	void setValorTest() {
		//Excersice
		pp.setValor(600);
		
		//verify
		assertEquals(pp.valor,600.0);
	}
	
	@Test
	void pertenceAlRangoTest() {

		//Setup
		LocalDate enero01 = LocalDate.of(2021, 1, 1);
		LocalDate enero31 = LocalDate.of(2021, 1, 31);
		LocalDate febrero1 = LocalDate.of(2021, 2, 1);
		LocalDate diciembre31 = LocalDate.of(2020, 12, 31);
		LocalDate enero15 = LocalDate.of(2021, 1, 15);
		
		//Excercise
		boolean e1 = pp.pertenceAlRango(enero01);
		boolean e2 = pp.pertenceAlRango(enero31);
		boolean e3 = pp.pertenceAlRango(febrero1);
		boolean e4 = pp.pertenceAlRango(diciembre31);
		boolean e5 = pp.pertenceAlRango(enero15);
		
		//verify
		assertTrue(e1);
		assertTrue(e2);
		assertFalse(e3);
		assertFalse(e4);
		assertTrue(e5);
		
	}
	
}
