package sensores;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.unq.sitioInmueble.GestorDeNotificaciones;
import clases.Inmueble;
import interfaces.IListenerBajaDePrecio;
import interfaces.ISuscriptorBajaDePrecio;
/*
public class SensorBajaDePrecio {

	private ISuscriptorBajaDePrecio suscriptor;
		
	public SensorBajaDePrecio(ISuscriptorBajaDePrecio suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	public void updateBajaDePrecio(Inmueble inmueble, double precio) {
		
		String mensaje = "No te pierdas esta oferta: Un inmueble " + inmueble.getTipo() + " a tan sólo "+ precio + " pesos";
		suscriptor.publish(mensaje);
		
	}

}
*/

public class SensorBajaDePrecio {
	
	
	private GestorDeNotificaciones miGestor;
	private HashMap<Inmueble, ArrayList<IListenerBajaDePrecio>> listeners;
	
	
	public SensorBajaDePrecio(GestorDeNotificaciones gestor) {
		this.miGestor = gestor;
		this.listeners = new HashMap<Inmueble, ArrayList<IListenerBajaDePrecio>>();
	}
	
	
	public HashMap<Inmueble, ArrayList<IListenerBajaDePrecio>> getListeners() {
		return this.listeners;
	}
	
	
	public void addSensorListener(Inmueble inmueble, IListenerBajaDePrecio listener) {
		if(!this.getListeners().containsKey(inmueble)) {
			this.getListeners().put(inmueble, new ArrayList<IListenerBajaDePrecio>());
		}
		this.getListeners().get(inmueble).add(listener);
	}

	public void removeSensorListener(Inmueble inmueble, IListenerBajaDePrecio listener) {
		if(this.getListeners().containsKey(inmueble)) {
			this.getListeners().get(inmueble).remove(listener);
		}
	}
	
	public void notificarBajaDePrecio(Inmueble inmueble, Double precio) {
		if(this.getListeners().containsKey(inmueble)) {
			for (IListenerBajaDePrecio listener : this.getListeners().get(inmueble)) {
				listener.publish("No te pierdas esta oferta: Un inmueble " + inmueble.getTipo() +
						" a tan solo " + precio + "pesos.");
			}
		}
	}

	
}
