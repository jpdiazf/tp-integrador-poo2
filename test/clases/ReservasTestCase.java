package clases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.IFormaDePago;
import interfaces.IRankeable;

class ReservasTestCase {

	Reserva reserva;
	Usuario inquilino;
	Usuario propietario;
	Inmueble inmueble;
	IFormaDePago formaDePago;
	PrecioPeriodo precio;
	
	@BeforeEach
	void setup() {
		inquilino = mock(Usuario.class);
		propietario = mock(Usuario.class);
		inmueble = mock(Inmueble.class);
		formaDePago = mock(IFormaDePago.class);
		when(inmueble.getPropietario()).thenReturn(propietario);
		when(inmueble.getPrecioPorPeriodo(any(), any())).thenReturn(500.0);
		
		reserva = new Reserva(inquilino, inmueble, formaDePago, LocalDate.of(2021,1,31), LocalDate.of(2021,2,20));
	
	}
	
	@Test
	void constructorTest() {
		//Excersice
		reserva = new Reserva(inquilino, inmueble, formaDePago, LocalDate.of(2021,1,31), LocalDate.of(2021,2,20));
		
		//Verify
		assertEquals(LocalDate.of(2021,1,31), reserva.getComienzo());
		assertEquals(LocalDate.of(2021,2,20), reserva.getFin());
		assertEquals(500.0, reserva.getPrecio());
		assertEquals(inmueble, reserva.getInmueble());
		assertEquals(inquilino, reserva.getInquilino());
		assertEquals(formaDePago, reserva.getFormaDePago());
		assertEquals(propietario, reserva.getPropietario());
		
	}

	
	@Test
	void aprobarTest() {
		
		//Excersice
		boolean estadoInicial = reserva.estaAceptada();
		reserva.aprobar();
		boolean aprobada = reserva.estaAceptada();
		reserva.rechazar();
		boolean rechazada = reserva.estaAceptada();
		
		//Verify
		assertFalse(estadoInicial);
		assertTrue(aprobada);
		assertFalse(rechazada);
	}
	
}
