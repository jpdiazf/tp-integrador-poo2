package ar.edu.unq.sitioInmueble;

import java.util.ArrayList;

import clases.Inmueble;
import clases.Reserva;
import clases.SensorBajaDePrecio;
import clases.SensorCancelacion;
import clases.SensorReserva;
import clases.Usuario;
import interfaces.ISuscriptorBajaDePrecio;
import interfaces.ISuscriptorCancelacion;
import interfaces.ISuscriptorReserva;

public class GestorDeNotificaciones {

	
	private SitioInmuebles sitioGestion;
	
	
	private ArrayList<SensorBajaDePrecio> sensoresBajasDePrecio = new ArrayList<SensorBajaDePrecio>();
	private ArrayList<SensorCancelacion> sensoresCancelaciones = new ArrayList<SensorCancelacion>();
	private ArrayList<SensorReserva> sensoresReservas = new ArrayList<SensorReserva>();
	
	
	public GestorDeNotificaciones(SitioInmuebles sitio) {
		this.sitioGestion = sitio;
	}
	
	public void notificarBajaDePrecio(Inmueble inmueble, Double nuevoPrecio) {
		for(SensorBajaDePrecio sensor:this.sensoresBajasDePrecio) {
			sensor.updateBajaDePrecio(inmueble, nuevoPrecio);
		}
	}
	
	public void notificarCancelacionDeReserva(Reserva reserva) {
		sensorCancelaciones.updateCancelacion(reserva);
	}
	
	public void notificarNuevaReserva(Reserva reserva) {
		sensorReservas.updateReservas(reserva);
	}
	
	public void suscribirBajaDePrecio(Inmueble inmueble, ISuscriptorBajaDePrecio suscriptor) {
		sensorBajasDePrecio.subscribe(inmueble, suscriptor); //PREGUNTAR
	}
	
	public void desuscribirBajaDePrecio(ISuscriptorBajaDePrecio suscriptor) {
		sensorBajasDePrecio.unsubscribe(suscriptor); //PREGUNTAR
	}
	
	public void suscribirCancelacionDeReserva(Reserva reserva, ISuscriptorCancelacion suscriptor) {
		sensorCancelaciones.subscribe(reserva, suscriptor);
	}
	
	public void desuscribirCancelacionDeReserva(Reserva reserva, ISuscriptorCancelacion suscriptor) {
		sensorCancelaciones.unsubscribe(reserva, suscriptor);
	}
	
	public void suscribirNuevaReserva(Inmueble inmueble, ISuscriptorReserva suscriptor) {
		sensorReservas.subscribe(inmueble, suscriptor);
	}
	
	public void desuscribirNuevaReserva(Inmueble inmueble, ISuscriptorReserva suscriptor) {
		sensorReservas.unsubscribe(inmueble, suscriptor);
	}
	
	
}
