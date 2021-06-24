package ar.edu.unq.sitioInmueble;

import clases.Inmueble;
import clases.Reserva;
import interfaces.IListenerBajaDePrecio;
import interfaces.IListenerCancelacion;
import interfaces.IListenerReserva;
import sensores.SensorBajaDePrecio;
import sensores.SensorCancelacion;
import sensores.SensorReserva;

public class GestorDeNotificaciones {

	
	private SitioInmuebles sitioGestion;
	
	private SensorBajaDePrecio sensorBajaDePrecio;
	private SensorCancelacion sensorCancelacion;
	private SensorReserva sensorReserva;
	
	public GestorDeNotificaciones(SitioInmuebles sitio) {
		this.sensorBajaDePrecio = new SensorBajaDePrecio();
		this.sensorCancelacion = new SensorCancelacion();
		this.sensorReserva = new SensorReserva();
	}
	
	public SitioInmuebles getSitio() {
		return this.sitioGestion;
	}
	
	
	//SUSCRIPCIONES
	
	public void suscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		sensorBajaDePrecio.addSensorListener(inmueble, listener); 
	}
	
	public void desuscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		sensorBajaDePrecio.removeSensorListener(inmueble, listener); 
	}
	
	public void suscribirCancelacion(Inmueble inmueble, IListenerCancelacion listener) {
		sensorCancelacion.addSensorListener(inmueble, listener); 
	}
	
	public void desuscribirCancelacion(Inmueble inmueble, IListenerCancelacion listener) {
		sensorCancelacion.removeSensorListener(inmueble, listener); 
	}
	
	public void suscribirReserva(Inmueble inmueble, IListenerReserva listener) {
		sensorReserva.addSensorListener(inmueble, listener);
	}
	
	public void desuscribirReserva(Inmueble inmueble, IListenerReserva listener) {
		sensorReserva.removeSensorListener(inmueble, listener);
	}
	
	
	//NOTIFICACIONES
	
	public void notificarBajaDePrecio(Inmueble inmueble, Double nuevoPrecio) {
		sensorBajaDePrecio.notificarBajaDePrecio(inmueble, nuevoPrecio);
	}
	
	public void notificarCancelacionDeReserva(Reserva reserva) {
		sensorCancelacion.notificarCancelacion(reserva);
	}
	
	public void notificarNuevaReserva(Reserva reserva) {
		sensorReserva.notificarReserva(reserva.getInmueble());
	}
	
	
}
