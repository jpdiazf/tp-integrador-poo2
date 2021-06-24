package clases;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;

import clases.Inmueble;
import clases.Reserva;
import clases.Usuario;
import interfaces.ISuscriptorBajaDePrecio;
import interfaces.ISuscriptorCancelacion;
import sensores.SensorBajaDePrecio;
import sensores.SensorCancelacion;
import sensores.SensorReserva;

class AlertasTestCase {
	
	SensorReserva sr1;
	SensorReserva sr2;
	SensorCancelacion sc1;
	SensorCancelacion sc2;
	SensorBajaDePrecio sp1;
	SensorBajaDePrecio sp2;
	Usuario suscriptorReserva1;
	Usuario suscriptorReserva2;
	ISuscriptorCancelacion suscriptorCancelacion1;
	ISuscriptorCancelacion suscriptorCancelacion2;
	ISuscriptorBajaDePrecio suscriptorBajaDePrecio1;
	ISuscriptorBajaDePrecio suscriptorBajaDePrecio2;
	Inmueble i1;
	Inmueble i2;
	Reserva r1;
	Reserva r2;
	
	@Test
	void sensorReservaTest() {
		
		//Setup
		suscriptorReserva1 = mock(Usuario.class);
		suscriptorReserva2 = mock(Usuario.class);
		i1 = mock(Inmueble.class);
		i2 = mock(Inmueble.class);
		r1 = mock(Reserva.class);
		r2 = mock(Reserva.class);
		
		sr1 = new SensorReserva(suscriptorReserva1);
		sr2 = new SensorReserva(suscriptorReserva2);

		when(i1.getPropietario()).thenReturn(suscriptorReserva1);
		when(i2.getPropietario()).thenReturn(suscriptorReserva2);		

		when(r1.getInmueble()).thenReturn(i1);
		when(r2.getInmueble()).thenReturn(i2);		
		
		//Excersice
		sr1.updateReservas(r1);
		sr2.updateReservas(r1);
		sr1.updateReservas(r2);
		sr2.updateReservas(r2);

		//Verify
		verify(suscriptorReserva1).recibirReserva(r1);
		verify(suscriptorReserva1, times(1)).recibirReserva(any());
		verify(suscriptorReserva2).recibirReserva(r2);
		verify(suscriptorReserva2, times(1)).recibirReserva(any());
		
	}

	
	@Test
	void sensorCancelacionTest() {
		
		
		//Setup
		suscriptorCancelacion1 = mock(ISuscriptorCancelacion.class);
		suscriptorCancelacion2 = mock(ISuscriptorCancelacion.class);
		i1 = mock(Inmueble.class);
		i2 = mock(Inmueble.class);
		r1 = mock(Reserva.class);
		r2 = mock(Reserva.class);
		
		when(i1.getTipo()).thenReturn("Casa");
		when(i2.getTipo()).thenReturn("Quincho");
		when(r1.getInmueble()).thenReturn(i1);
		when(r2.getInmueble()).thenReturn(i2);	
		
		sc1 = new SensorCancelacion(suscriptorCancelacion1, "Rojo", 5, i1);
		sc2 = new SensorCancelacion(suscriptorCancelacion2, "Verde", 10, i2);

		String msg1 = "El/la Casa que te interesa se ha liberado! Corre a reservarlo!";
		String msg2 = "El/la Quincho que te interesa se ha liberado! Corre a reservarlo!";

		//Excersice
		sc1.updateCancelacion(r1);
		sc2.updateCancelacion(r1);
		sc1.updateCancelacion(r2);
		sc2.updateCancelacion(r2);
		
		//Verify
		verify(suscriptorCancelacion1).popUp(msg1, "Rojo", 5);
		verify(suscriptorCancelacion1, times(0)).popUp(msg2, "Verde", 10);
		verify(suscriptorCancelacion2).popUp(msg2, "Verde", 10);
		verify(suscriptorCancelacion2, times(0)).popUp(msg1, "Rojo", 5);
	
	}
	
	@Test
	void sensorBajaDePrecio() {
		
		//Setup
		suscriptorBajaDePrecio1 = mock(ISuscriptorBajaDePrecio.class);
		suscriptorBajaDePrecio2 = mock(ISuscriptorBajaDePrecio.class);
		i1 = mock(Inmueble.class);
		i2 = mock(Inmueble.class);
		
		when(i1.getTipo()).thenReturn("Casa");
		when(i2.getTipo()).thenReturn("Quincho");
		
		sp1 = new SensorBajaDePrecio(suscriptorBajaDePrecio1);
		sp2 = new SensorBajaDePrecio(suscriptorBajaDePrecio2);

		String msg1 = "No te pierdas esta oferta: Un inmueble Casa a tan sólo 500.0 pesos";
		String msg2 = "No te pierdas esta oferta: Un inmueble Quincho a tan sólo 1000.0 pesos";

		//Excersice
		sp1.updateBajaDePrecio(i1, 500.0);
		sp2.updateBajaDePrecio(i1, 500.0);
		sp1.updateBajaDePrecio(i2, 1000.0);
		sp2.updateBajaDePrecio(i2, 1000.0);
		
		//Verify
		verify(suscriptorBajaDePrecio1).publish(msg1);
		verify(suscriptorBajaDePrecio2).publish(msg2);
		verify(suscriptorBajaDePrecio1).publish(msg1);
		verify(suscriptorBajaDePrecio2).publish(msg2);
	
	}

}
