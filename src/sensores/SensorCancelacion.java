package sensores;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.unq.sitioInmueble.GestorDeNotificaciones;
import clases.Inmueble;
import clases.Reserva;
import interfaces.IListenerCancelacion;
import interfaces.ISuscriptorCancelacion;

/*
public class SensorCancelacion {
	
	private ISuscriptorCancelacion suscriptor;
	private String colorFuente;
	private int tamanioFuente;
	private Inmueble inmuebleDeInteres;
	
	public SensorCancelacion(ISuscriptorCancelacion suscriptor, String colorFuente, int tamanioFuente, Inmueble inmuebleDeInteres) {
		this.suscriptor = suscriptor;
		this.colorFuente = colorFuente;
		this.tamanioFuente = tamanioFuente;
		this.inmuebleDeInteres = inmuebleDeInteres;
	}
	
	public void updateCancelacion(Reserva reserva) {
		if(reserva.getInmueble().equals(this.inmuebleDeInteres)) {
			
			String message = "El/la " + reserva.getInmueble().getTipo() + " que te interesa se ha liberado! Corre a reservarlo!";
			
			suscriptor.popUp(message, this.colorFuente, this.tamanioFuente);
		}
	}

}
*/

public class SensorCancelacion {
	
	private GestorDeNotificaciones miGestor;
	private HashMap<Inmueble, ArrayList<IListenerCancelacion>> listeners;
	private String colorFuente;
	private int tamanioFuente;
	
	
	public SensorCancelacion(GestorDeNotificaciones gestor) {
		this.miGestor = gestor;
		this.colorFuente = "Negro";
		this.tamanioFuente = 12;
	}
	
	public SensorCancelacion(GestorDeNotificaciones gestor, String colorFuente, int tamanioFuente) {
		this.miGestor = gestor;
		this.colorFuente = colorFuente;
		this.tamanioFuente = tamanioFuente;
		this.listeners = new HashMap<Inmueble, ArrayList<IListenerCancelacion>>();
	}
	
	
	public HashMap<Inmueble, ArrayList<IListenerCancelacion>> getListeners() {
		return this.listeners;
	}
	
	
	public void addSensorListener(Inmueble inmueble, IListenerCancelacion listener) {
		if(!this.getListeners().containsKey(inmueble)) {
			this.getListeners().put(inmueble, new ArrayList<IListenerCancelacion>());
		}
		this.getListeners().get(inmueble).add(listener);
	}

	public void removeSensorListener(Inmueble inmueble, IListenerCancelacion listener) {
		if(this.getListeners().containsKey(inmueble)) {
			this.getListeners().get(inmueble).remove(listener);
		}
	}
	
	public void notificarCancelacion(Reserva reserva) {
		if(this.getListeners().containsKey(reserva.getInmueble())) {
			
			String message = "El/la " + reserva.getInmueble().getTipo() + " que te interesa se ha liberado! Corre a reservarlo!";
					
			for (IListenerCancelacion listener : this.getListeners().get(reserva)) {
				listener.popUp(message, this.colorFuente, this.tamanioFuente);
			}
			
		}
	}
	
	
}