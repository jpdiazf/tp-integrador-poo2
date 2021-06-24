package sensores;

import java.util.ArrayList;
import java.util.HashMap;
import clases.Inmueble;
import interfaces.IListenerBajaDePrecio;

public class SensorBajaDePrecio {
	
	
	private HashMap<Inmueble, ArrayList<IListenerBajaDePrecio>> listeners;
	
	
	public SensorBajaDePrecio() {
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
						" a tan sólo " + precio + " pesos");
			}
		}
	}

	
}
