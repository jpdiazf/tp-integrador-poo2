package ar.edu.unq.sitioInmueble;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Inmueble;
import clases.PoliticaCancelacion;
import clases.Reserva;
import clases.Usuario;

class GestorDeReservasTest {

	
	private GestorDeReservas gestor;
	
	private SitioInmuebles sitioGestion;
	
	private Reserva reservaCasa;
	private Reserva reservaDepto;
	private Reserva reservaHabitacion;
	
	private Inmueble casa;
	private Inmueble depto;
	private Inmueble habitacion;
	
	
	@BeforeEach
	void setUp() {
		
		sitioGestion = mock(SitioInmuebles.class);
		
		gestor = new GestorDeReservas(sitioGestion);
		
		reservaCasa = mock(Reserva.class);
		reservaDepto = mock(Reserva.class);
		reservaHabitacion = mock(Reserva.class);
		
		casa = mock(Inmueble.class);
		depto = mock(Inmueble.class);
		habitacion = mock(Inmueble.class);
		
		when(reservaCasa.getInmueble()).thenReturn(casa);
		when(reservaDepto.getInmueble()).thenReturn(depto);
		when(reservaHabitacion.getInmueble()).thenReturn(habitacion);
		
	}
	
	
	@Test
	void testConstructor() {
		
		assertEquals(gestor.getSitioGestion(), sitioGestion);
		assertTrue(gestor.getReservas().size() == 0);
		
	}
	
	
	@Test 
	void testRecibimientoReservas() throws Exception{
		
		//RESERVA CON ÉXITO
		
		//Preparación Double del site
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		//Preparación Double del inmueble
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
		
		gestor.recibirReserva(reservaCasa);
		
		verify(sitioGestion, times(1)).estaDadoDeAlta(casa);
		verify(casa, times(1)).estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin());
		
		verify(sitioGestion, times(1)).notificarNuevaReserva(reservaCasa);
		
		assertTrue(gestor.getReservas().contains(reservaCasa) && gestor.getReservas().size() == 1);
		
		
		//RESERVA CON ERROR
		
		//NO DADO DE ALTA EN EL SITIO
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(false);
		//PUBLICADA
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
		
		when(casa.getInformacion()).thenReturn("casa");
		
		Exception exc1 = assertThrows(Exception.class, () -> gestor.recibirReserva(reservaCasa));
		
		assertEquals("No se encuentra disponible para reservar el inmueble casa", exc1.getMessage());
		
		
		//DADO DE ALTA EN EL SITIO
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		//NO PUBLICADA
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(false);
		
		Exception exc2 = assertThrows(Exception.class, () -> gestor.recibirReserva(reservaCasa));
		
		assertEquals("No se encuentra disponible para reservar el inmueble casa", exc2.getMessage());
		
	}
	
	
	@Test
	void aprobacionORechazoReservas() throws Exception{
		
		//APROBACIÓN CON ÉXITO
		
		//Se recibe la reserva ya testeada previamente
		
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
		
		gestor.recibirReserva(reservaCasa);
		
		//Se desea aprobarla
		//mockeo el propietario
		
		Usuario propietarioCasa = mock(Usuario.class);
		when(reservaCasa.getPropietario()).thenReturn(propietarioCasa);
		//NO debe estar aceptada la reserva...
		when(reservaCasa.estaAceptada()).thenReturn(false);
		
		gestor.aprobarReserva(reservaCasa);
		
		//Se verifica su aprobación
		verify(reservaCasa, times(1)).aprobar();
		
		
		//APROBACIÓN CON ERROR
		
		Reserva reservaPrueba = mock(Reserva.class);
		Inmueble inmueblePrueba = mock(Inmueble.class);
		Usuario propietarioFantasma = mock(Usuario.class);
		when(reservaPrueba.getPropietario()).thenReturn(propietarioFantasma);
		
		when(reservaPrueba.getInmueble()).thenReturn(inmueblePrueba);
		when(inmueblePrueba.getInformacion()).thenReturn("prueba");
		
		//No es dada de alta, por lo que no puede aprobarse.
		
		Exception err1 = assertThrows(Exception.class, () -> gestor.aprobarReserva(reservaPrueba));
		
		assertEquals("No se encuentra una reserva del inmueble prueba", err1.getMessage());
		
		//Es dada de alta, ¡pero ya se encuentra aceptada!
		when(sitioGestion.estaDadoDeAlta(inmueblePrueba)).thenReturn(true);
		when(inmueblePrueba.estaPublicadoPeriodo(reservaPrueba.getComienzo(), reservaPrueba.getFin())).thenReturn(true);
		gestor.recibirReserva(reservaPrueba);
		when(reservaPrueba.estaAceptada()).thenReturn(true);
		
		Exception err2 = assertThrows(Exception.class, () -> gestor.aprobarReserva(reservaPrueba));
		
		assertEquals("La reserva ya se encuentra aceptada", err2.getMessage());
		
	}
	
	
	@Test
	void rechazoReservas() throws Exception {
		
		//RECHAZO CON ÉXITO
		
		//Se recibe la reserva ya testeada previamente
		
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
				
		gestor.recibirReserva(reservaCasa);
		
		Usuario propietarioCasa = mock(Usuario.class);
		when(reservaCasa.getPropietario()).thenReturn(propietarioCasa);
		//NO debe estar aceptada la reserva...
		when(reservaCasa.estaAceptada()).thenReturn(false);
		
		assertTrue(gestor.getReservas().contains(reservaCasa));
		
		gestor.rechazarReserva(reservaCasa);
		
		assertFalse(gestor.getReservas().contains(reservaCasa));
		
	}
	
	
	@Test
	void cancelacionReservas() throws Exception {
		
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
				
		gestor.recibirReserva(reservaCasa);
		
		Usuario propietarioCasa = mock(Usuario.class);
		when(reservaCasa.getPropietario()).thenReturn(propietarioCasa);
		
		//está en el gestor
		assertTrue(gestor.getReservas().contains(reservaCasa));
		
		gestor.cancelarReserva(reservaCasa);
		
		//ya se canceló
		assertFalse(gestor.getReservas().contains(reservaCasa));
		
		//la misma reserva se cancela y se delega el pago con su política de cancelación correspondiente...
		verify(reservaCasa, times(1)).cancelar();
		
		verify(sitioGestion, times(1)).notificarCancelacionDeReserva(reservaCasa);
		
	}
	
	
	@Test
	void consultasSobreReservas() throws Exception{
		
		//Se recibe la reserva ya testeada previamente
		
		when(sitioGestion.estaDadoDeAlta(casa)).thenReturn(true);
		when(casa.estaPublicadoPeriodo(reservaCasa.getComienzo(), reservaCasa.getFin())).thenReturn(true);
						
		gestor.recibirReserva(reservaCasa);
		
		//y siguiendo con la misma lógica, se agregan 2 reservas más...
		
		when(sitioGestion.estaDadoDeAlta(depto)).thenReturn(true);
		when(depto.estaPublicadoPeriodo(reservaDepto.getComienzo(), reservaDepto.getFin())).thenReturn(true);
		
		gestor.recibirReserva(reservaDepto);
		
		when(sitioGestion.estaDadoDeAlta(habitacion)).thenReturn(true);
		when(habitacion.estaPublicadoPeriodo(reservaHabitacion.getComienzo(), reservaHabitacion.getFin())).thenReturn(true);
		
		gestor.recibirReserva(reservaHabitacion);
		
		//Mock sobre fechas para assertions
		LocalDate hoy = LocalDate.now();
		
		//Reserva de la casa desde ayer hasta 3 días más adelante.
		when(reservaCasa.getComienzo()).thenReturn(hoy.minusDays(1));
		when(reservaCasa.getFin()).thenReturn(hoy.plusDays(3));
		
		//Reserva del depto desde pasado mañana hasta 5 días más adelante.
		when(reservaDepto.getComienzo()).thenReturn(hoy.plusDays(2));
		when(reservaDepto.getFin()).thenReturn(hoy.plusDays(7));
		
		//Reserva de la habitación desde hoy hasta pasado mañana.
		when(reservaHabitacion.getComienzo()).thenReturn(hoy.plusDays(1));
		when(reservaHabitacion.getFin()).thenReturn(hoy.plusDays(2));
		
		
		assertTrue(gestor.hayReservaEn(casa, hoy, hoy.plusDays(1)));
		
		assertTrue(gestor.hayReservaEn(casa, hoy.minusDays(3), hoy));
		
		assertTrue(gestor.hayReservaEn(casa, hoy, hoy.plusDays(8)));
	
		assertTrue(gestor.hayReservaEn(casa, hoy.minusDays(5), hoy));
		
		assertTrue(gestor.hayReservaEn(casa, hoy.plusDays(2), hoy.plusDays(10)));
		
		
		assertFalse(gestor.hayReservaEn(depto, hoy, hoy.plusDays(1)));
		
		assertFalse(gestor.hayReservaEn(depto, hoy.plusDays(8), hoy.plusDays(11)));
		
		
		//También funciona con la reserva de un inmueble que no esté reservado.
		
		assertFalse(gestor.hayReservaEn(mock(Inmueble.class), hoy, hoy.plusDays(1)));
		
		
		//Reservas de hoy
		
		assertTrue(gestor.hayReservaHoy(casa));
		assertFalse(gestor.hayReservaHoy(habitacion));
		assertFalse(gestor.hayReservaHoy(depto));
		
		
		assertFalse(gestor.hayReservaHoy(mock(Inmueble.class)));
		
	}
	

}
