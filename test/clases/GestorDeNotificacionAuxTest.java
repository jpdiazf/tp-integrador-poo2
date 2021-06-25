package clases;

import ar.edu.unq.sitioInmueble.GestorDeNotificaciones;
import sensores.SensorBajaDePrecio;
import sensores.SensorCancelacion;
import sensores.SensorReserva;

public class GestorDeNotificacionAuxTest extends GestorDeNotificaciones {
	
	public GestorDeNotificacionAuxTest() {
		super();
	}

	public void setSensorBajaDePrecio(SensorBajaDePrecio s) {
		this.sensorBajaDePrecio = s;
	}
	public void setSensorCancelacion(SensorCancelacion s) {
		this.sensorCancelacion = s;
	}
	public void setSensorReserva(SensorReserva s) {
		this.sensorReserva = s;
	}
}
