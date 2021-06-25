package ar.edu.unq.sitioInmueble;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.GestorDeNotificacionAuxTest;
import clases.Inmueble;
import clases.Reserva;
import clases.Usuario;
import interfaces.IListenerBajaDePrecio;
import interfaces.IListenerCancelacion;
import interfaces.IListenerReserva;
import sensores.SensorBajaDePrecio;
import sensores.SensorCancelacion;
import sensores.SensorReserva;

class GestorDeNotificacionesTestCase {

	GestorDeNotificacionAuxTest gestor;
	SensorBajaDePrecio sBajaDePrecio;
	SensorCancelacion sCancelacion;
	SensorReserva sReserva;
	IListenerReserva suscriptorReserva;
	IListenerCancelacion suscriptorCancelacion;
	IListenerBajaDePrecio suscriptorBajaDePrecio;
	Inmueble inmueble;
	Reserva reserva;
	
	@BeforeEach()
	void setup() {
		gestor = new GestorDeNotificacionAuxTest();
		sBajaDePrecio = mock(SensorBajaDePrecio.class);
		sCancelacion = mock(SensorCancelacion.class);
		sReserva = mock(SensorReserva.class);
		suscriptorReserva = mock(Usuario.class);
		suscriptorCancelacion = mock(IListenerCancelacion.class); 
		suscriptorBajaDePrecio = mock(IListenerBajaDePrecio.class);
		inmueble = mock(Inmueble.class);
		gestor.setSensorBajaDePrecio(sBajaDePrecio);
		gestor.setSensorCancelacion(sCancelacion);
		gestor.setSensorReserva(sReserva);
	}
	
	@Test
	void suscripcionesTest() {
	
		//Excersice
		gestor.suscribirBajaDePrecio(inmueble, suscriptorBajaDePrecio);
		gestor.suscribirCancelacion(inmueble, suscriptorCancelacion);
		gestor.suscribirReserva(suscriptorReserva);
		
		//Verify
		verify(sBajaDePrecio).addSensorListener(inmueble, suscriptorBajaDePrecio);
		verify(sBajaDePrecio, times(1)).addSensorListener(any(), any());
		verify(sCancelacion).addSensorListener(inmueble, suscriptorCancelacion);
		verify(sCancelacion, times(1)).addSensorListener(any(), any());
		verify(sReserva).addSensorListener(suscriptorReserva);
		verify(sReserva, times(1)).addSensorListener(any());
		
	}
	
	@Test
	void desuscribirTest() {
	
		
		//Excersice
		gestor.desuscribirBajaDePrecio(inmueble, suscriptorBajaDePrecio);
		gestor.desuscribirCancelacion(inmueble, suscriptorCancelacion);
		gestor.desuscribirReserva(suscriptorReserva);
		
		//Verify
		verify(sBajaDePrecio).removeSensorListener(inmueble, suscriptorBajaDePrecio);
		verify(sBajaDePrecio, times(1)).removeSensorListener(any(), any());
		verify(sCancelacion).removeSensorListener(inmueble, suscriptorCancelacion);
		verify(sCancelacion, times(1)).removeSensorListener(any(), any());
		verify(sReserva).removeSensorListener(suscriptorReserva);
		verify(sReserva, times(1)).removeSensorListener(any());
		
	}
	@Test
	void notificacionesTest() {
	
		//Setup
		reserva = mock(Reserva.class);
		
		//Excersice
		gestor.notificarBajaDePrecio(inmueble, 500.0);
		gestor.notificarCancelacionDeReserva(reserva);
		gestor.notificarNuevaReserva(reserva);
		
		//Verify
		verify(sBajaDePrecio).notificarBajaDePrecio(inmueble, 500.0);
		verify(sBajaDePrecio, times(1)).notificarBajaDePrecio(any(), any());
		verify(sCancelacion).notificarCancelacion(reserva);
		verify(sCancelacion, times(1)).notificarCancelacion(any());
		verify(sReserva).notificarReserva(reserva);
		verify(sReserva, times(1)).notificarReserva(any());
		
	}

}
