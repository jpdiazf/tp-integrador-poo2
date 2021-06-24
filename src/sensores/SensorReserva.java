package sensores;
import java.util.ArrayList;
import java.util.HashMap;

import ar.edu.unq.sitioInmueble.GestorDeNotificaciones;
import clases.Inmueble;
import interfaces.IListenerReserva;
import interfaces.ISuscriptorReserva;
/*
public class SensorReserva {

	private ISuscriptorReserva suscriptor;
	
	public SensorReserva(ISuscriptorReserva suscriptor) {
		this.suscriptor = suscriptor;
	}
	
	public void updateReservas(Reserva reserva) {
		if(reserva.getInmueble().getPropietario().equals(this.suscriptor)) {
			this.suscriptor.recibirReserva(reserva);
		}
	}

}
*/

public class SensorReserva {
	
	
	private GestorDeNotificaciones miGestor;
	private HashMap<Inmueble, ArrayList<IListenerReserva>> listeners;
	
	
	public SensorReserva(GestorDeNotificaciones gestor) {
		this.miGestor = gestor;
		this.listeners = new HashMap<Inmueble, ArrayList<IListenerReserva>>();
	}
	
	
	public HashMap<Inmueble, ArrayList<IListenerReserva>> getListeners() {
		return this.listeners;
	}
	
	
	public void addSensorListener(Inmueble inmueble, IListenerReserva listener) {
		if(!this.getListeners().containsKey(inmueble)) {
			this.getListeners().put(inmueble, new ArrayList<IListenerReserva>());
		}
		this.getListeners().get(inmueble).add(listener);
	}

	public void removeSensorListener(Inmueble inmueble, IListenerReserva listener) {
		if(this.getListeners().containsKey(inmueble)) {
			this.getListeners().get(inmueble).remove(listener);
		}
	}
	
	public void notificarReserva(Inmueble inmueble) {
		if(this.getListeners().containsKey(inmueble)) {
			for (IListenerReserva listener : this.getListeners().get(inmueble)) {
				listener.update("El/la " + inmueble.getTipo() + " que te interesa fue reservado");
			}
		}
	}
	
}