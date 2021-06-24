package ar.edu.unq.sitioInmueble;

import java.util.ArrayList;

import clases.Inmueble;
import clases.Reserva;
import clases.Usuario;
import interfaces.IListenerBajaDePrecio;
import interfaces.IListenerCancelacion;
import interfaces.IListenerReserva;
import interfaces.ISuscriptorBajaDePrecio;
import interfaces.ISuscriptorCancelacion;
import interfaces.ISuscriptorReserva;
import sensores.SensorBajaDePrecio;
import sensores.SensorCancelacion;
import sensores.SensorReserva;

public class GestorDeNotificaciones {

	
	private SitioInmuebles sitioGestion;
	
	
	private SensorBajaDePrecio sensorBajaDePrecio;
	private SensorCancelacion sensorCancelacion;
	private SensorReserva sensorReserva;
	
	
	public GestorDeNotificaciones(SitioInmuebles sitio) {
		this.sitioGestion = sitio;
		this.sensorBajaDePrecio = new SensorBajaDePrecio(this);
		this.sensorCancelacion = new SensorCancelacion(this);
		this.sensorReserva = new SensorReserva(this);
	}
	
	public SitioInmuebles getSitio() {
		return this.sitioGestion;
	}
	
	
	//SUSCRIPCIONES
	
	public void suscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		sensorBajaDePrecio.addSensorListener(inmueble, listener); //PREGUNTAR
	}
	
	public void desuscribirBajaDePrecio(Inmueble inmueble, IListenerBajaDePrecio listener) {
		sensorBajaDePrecio.removeSensorListener(inmueble, listener); //PREGUNTAR
	}
	
	public void suscribirCancelacion(Inmueble inmueble, IListenerCancelacion listener) {
		sensorCancelacion.addSensorListener(inmueble, listener); //PREGUNTAR
	}
	
	public void desuscribirCancelacion(Inmueble inmueble, IListenerCancelacion listener) {
		sensorCancelacion.removeSensorListener(inmueble, listener); //PREGUNTAR
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
