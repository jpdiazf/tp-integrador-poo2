package sensores;

import java.util.ArrayList;
import java.util.HashMap;
import clases.Inmueble;
import clases.Reserva;
import interfaces.IListenerCancelacion;

public class SensorCancelacion {
	
	private HashMap<Inmueble, ArrayList<IListenerCancelacion>> listeners;
	
	public SensorCancelacion() {
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
					
			for (IListenerCancelacion listener : this.getListeners().get(reserva.getInmueble())) {
				listener.popUp(message, listener.colorFuente(), listener.tamanioFuente());
			}
			
		}
	}
	
	
}