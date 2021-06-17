

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CancelacionTestCase {

	Reserva r;
	Cancelacion c;

	
	@Test
	void sinCancelacionTest() {
		
		//Setup
		c = new SinCancelacion();
		r = mock(Reserva.class);
		
		when( r.comienzo() ).thenReturn(null);
		when( r.fin() ).thenReturn(null);
		when( r.valor() ).thenReturn((double) 5000);
		
		//Excersice
		double valor = c.valor(r);
		
		//Verify
		assertEquals(5000, valor);
		
	}

	@Test
	void cancelacionGratuitaTest() {

		//Cancelación gratuita hasta 10 días antes de la fecha de inicio de la
		//ocupación y luego abona el equivalente a dos días de reserva
		
		//Setup
		c = new CancelacionGratuita();
		r = mock(Reserva.class);
		when( r.valor() ).thenReturn((double) 5000);

		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(10));
		when( r.fin() ).thenReturn(LocalDate.now().plusDays(20));

		//Excersice
		double valorALos10Dias = c.valor(r);
			
		//Setup
		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(9));
		when( r.fin() ).thenReturn(LocalDate.now().plusDays(19));
		
		//Excersice
		double valorALos9Dias = c.valor(r);
		
		//Verify
		assertEquals(0, valorALos10Dias);
		assertEquals(1000.0, valorALos9Dias);		

	}
	
	@Test
	void cancelacionIntermediaTest() {
		
		//Hasta 20 días antes es gratuita, entre el día 19 anterior y el
		//día 10 anterior paga el 50 %, después del 10mo día paga la totalidad.
		
		//Setup
		c = new CancelacionIntermedia();
		r = mock(Reserva.class);
		when( r.valor() ).thenReturn((double) 5000);

		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(20));
		when( r.fin() ).thenReturn(null);

		//Excersice
		double valorALos20Dias = c.valor(r);
			
		//Setup
		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(19));
		when( r.fin() ).thenReturn(null);
		
		//Excersice
		double valorALos19Dias = c.valor(r);

		//Setup
		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(10));
		when( r.fin() ).thenReturn(null);
		
		//Excersice
		double valorALos10Dias = c.valor(r);		
		
		//Setup
		when( r.comienzo() ).thenReturn(LocalDate.now().plusDays(9));
		when( r.fin() ).thenReturn(null);
		
		//Excersice
		double valorALos9Dias = c.valor(r);	
		
		//Verify
		assertEquals(0, valorALos20Dias);
		assertEquals(2500.0, valorALos19Dias);	
		assertEquals(2500.0, valorALos10Dias);
		assertEquals(5000.0, valorALos9Dias);
	}
	
}
