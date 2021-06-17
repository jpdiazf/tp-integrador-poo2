

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PrecioPeriodoTestCase {

	PrecioPeriodo pp1;
	
	@Test
	void pertenceAlRangoTest() {

		//Setup
		
		pp1 = new PrecioPeriodo(LocalDate.of(2021, 1, 1), LocalDate.of(2021,1,31), 500);
		
		LocalDate enero01 = LocalDate.of(2021, 1, 1);
		LocalDate enero31 = LocalDate.of(2021, 1, 31);
		LocalDate febrero1 = LocalDate.of(2021, 2, 1);
		LocalDate diciembre31 = LocalDate.of(2020, 12, 31);
		LocalDate enero15 = LocalDate.of(2021, 1, 15);
		
		//Excercise
		boolean e1 = pp1.pertenceAlRango(enero01);
		boolean e2 = pp1.pertenceAlRango(enero31);
		boolean e3 = pp1.pertenceAlRango(febrero1);
		boolean e4 = pp1.pertenceAlRango(diciembre31);
		boolean e5 = pp1.pertenceAlRango(enero15);
		
		//verify
		assertTrue(e1);
		assertTrue(e2);
		assertFalse(e3);
		assertFalse(e4);
		assertTrue(e5);
		
	}

}
