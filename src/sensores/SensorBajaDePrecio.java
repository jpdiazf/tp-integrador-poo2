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
	
	public void addSensorListener(Inmueble inmueble, IListenerBajaDePrecio listener) {
		if(!this.listeners.containsKey(inmueble)) {
			this.listeners.put(inmueble, new ArrayList<IListenerBajaDePrecio>());
		}
		this.listeners.get(inmueble).add(listener);
	}

	public void removeSensorListener(Inmueble inmueble, IListenerBajaDePrecio listener) {
		if(this.listeners.containsKey(inmueble)) {
			this.listeners.get(inmueble).remove(listener);
		}
	}
	
	public void notificarBajaDePrecio(Inmueble inmueble, Double precio) {
		if(this.listeners.containsKey(inmueble)) {
			for (IListenerBajaDePrecio listener : this.listeners.get(inmueble)) {
				listener.publish("No te pierdas esta oferta: Un inmueble " + inmueble.getTipo() +
						" a tan sólo " + precio + " pesos");
			}
		}
	}

	
}
