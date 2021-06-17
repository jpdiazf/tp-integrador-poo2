import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class AlertasTestCase {
	
	SensorReserva sr1;
	SensorReserva sr2;
	SensorCancelacion sc1;
	SensorCancelacion sc2;
	SensorBajaDePrecio sp1;
	SensorBajaDePrecio sp2;
	ISuscriptorReserva suscriptorReserva1;
	ISuscriptorReserva suscriptorReserva2;
	ISuscriptorCancelacion suscriptorCancelacion1;
	ISuscriptorCancelacion suscriptorCancelacion2;
	ISuscriptorBajaDePrecio suscriptorBajaDePrecio1;
	ISuscriptorBajaDePrecio suscriptorBajaDePrecio2;
	Inmueble i1;
	Inmueble i2;
	
	@Test
	void sensorReservaTest() {
		
		suscriptorReserva1 = mock(ISuscriptorReserva.class);
		suscriptorReserva2 = mock(ISuscriptorReserva.class);

		fail("FALTA DEFINIR LA INTERFAZ DEL USUARIO");
	}

	
	@Test
	void sensorCancelacionTest() {
		
		
		//Setup
		suscriptorCancelacion1 = mock(ISuscriptorCancelacion.class);
		suscriptorCancelacion2 = mock(ISuscriptorCancelacion.class);
		i1 = mock(Inmueble.class);
		i2 = mock(Inmueble.class);
		
		when(i1.getTipo()).thenReturn("Casa");
		when(i2.getTipo()).thenReturn("Quincho");
		
		sc1 = new SensorCancelacion(suscriptorCancelacion1, "Rojo", 5, i1);
		sc1 = new SensorCancelacion(suscriptorCancelacion1, "Verde", 10, i2);
		
		//Excersice
		
		fail("FALTA DEFINIR LA INTERFAZ DEL USUARIO");
	}

}
